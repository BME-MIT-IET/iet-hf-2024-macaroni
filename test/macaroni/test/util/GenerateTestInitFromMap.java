package macaroni.test.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import macaroni.model.character.Plumber;
import macaroni.model.character.Saboteur;
import macaroni.model.effects.Effect;
import macaroni.model.element.Cistern;
import macaroni.model.element.Pipe;
import macaroni.model.element.Pump;
import macaroni.model.element.Spring;
import macaroni.model.misc.WaterCollector;
import macaroni.views.Position;

/**
 * Generate unit test initializer from map data
 * */
public class GenerateTestInitFromMap {
	public static void main(String[] args) throws IOException {
		File fs = new File("tests/Basic Actions");
		System.out.println("@BeforeEach");
		System.out.println("void setup() {");
		System.out.println("ModelObjectFactory.reset();");
		System.out.println("}");
		System.out.println();

		for (File f : fs.listFiles(f -> f.getName().endsWith(".pasta") && !f.getName().endsWith(".config.pasta"))) {
			System.out.println("@Test");
			System.out.println("void " + f.getName().substring(0, f.getName().length() - 6).replaceAll("[^a-zA-Z]", "") + "() {");
			main(f);
			System.out.println("}");
			System.out.println();
		}
	}

	public static void main(File file) throws IOException {
		Map<String, Class<?>> objectMap = new HashMap<>();
		objectMap.put("pump", Pump.class);
		objectMap.put("newPipe", Pipe.class);
		List<String> initializers = new ArrayList<>();

		String[] lines = Files.readString(file.toPath()).split("\n");
		for (String line : lines) {
			if (line.isBlank() || line.startsWith("#")) {
				continue;
			}
			if (line.startsWith("@")) {
				if (line.startsWith("@!")) {
					initializers.add("assertNull(ModelObjectFactory.getObject(\"" + line.substring(2).strip() + "\"));");
				} else {
					int i = line.indexOf('.');
					String obj = line.substring(1, i).strip();
					String field = line.substring(i + 1).strip();
					String[] attribData = field.split("=");
					getObjectField(objectMap, initializers, obj, attribData[0].strip(), attribData.length < 2 ? "" : attribData[1].strip());
				}
			} else if(line.startsWith("load")) {
				convertWorld(objectMap, initializers, new File(file.getParentFile(), line.substring(5).strip()));
			} else {
				initializers.add("//" + line.strip());
			}
		}

		initializers.forEach(System.out::println);
	}

	public static void convertWorld(Map<String, Class<?>> objectMap, List<String> initializers, File file) throws IOException {
		String[] lines = Files.readString(file.toPath()).split("\n");

		Pattern posRegex = Pattern.compile("x\\s*=\\s*(\\d+)\\s*,\\s*y\\s*=\\s*(\\d+)");
		Position pos = null;

		// create objects
		for (String line : lines) {
			if (line.startsWith("#")) {
				Matcher matcher = posRegex.matcher(line);
				if (matcher.find()) {
					pos = new Position(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
				}
			}
			if (line.contains(":")) {
				String[] objInfo = line.split(":");
				createObject(objectMap, initializers, objInfo[1].strip(), objInfo[0].strip(), pos);
				pos = null;
			}
		}

		// set fields for objects
		String currentObjectName = null;
		for (String line : lines) {
			if (!line.startsWith("#") && line.contains("=")) {
				// if line starts with tab, then it is an attribute name-value pair,
				// set field of the current object
				String[] attribData = line.split("=");
				setObjectField(objectMap, initializers, currentObjectName, attribData[0].strip(), attribData.length < 2 ? "" : attribData[1].strip());
			} else {
				currentObjectName = line.split(":")[0].strip();
			}
		}
	}

	private static void createObject(Map<String, Class<?>> objectMap, List<String> initializers, String type, String name, Position pos) {
		String created = switch (type) {
		case "Pipe" -> "new Pipe(null)";
		case "Pump" -> "new Pump()";
		case "Spring" -> "new Spring()";
		case "Cistern" -> "new Cistern(null)";
		case "Plumber" -> "new Plumber(null)";
		case "Saboteur" -> "new Saboteur(null)";
		case "WaterCollector" -> "new WaterCollector()";
		default -> throw new RuntimeException("Invalid object type found in map file: " + type);
		};
		Class<?> clazz = switch (type) {
		case "Pipe" -> Pipe.class;
		case "Pump" -> Pump.class;
		case "Spring" -> Spring.class;
		case "Cistern" -> Cistern.class;
		case "Plumber" -> Plumber.class;
		case "Saboteur" -> Saboteur.class;
		case "WaterCollector" -> WaterCollector.class;
		default -> throw new RuntimeException("Invalid object type found in map file: " + type);
		};
		initializers.add("var " + name + " = " + created + ";");
		objectMap.put(name, clazz);
	}

	private static void getObjectField(Map<String, Class<?>> objectMap, List<String> asserts, String name, String fieldName, String fieldValue) {
		// go through class and all of its superclasses
		for (Class<?> reflection = objectMap.get(name); reflection != null; reflection = reflection.getSuperclass()) {
			// search for field
			for (Field field : reflection.getDeclaredFields()) {
				if (!field.getName().equals(fieldName)) continue;
				String fv = deserializeAttribute(objectMap, field, field.getType(), fieldValue);
				String r = "ReflectionUtil.getPrivateField(" + reflection.getSimpleName() + ".class, $this, \"" + fieldName + "\")";
				if (fv == null || fv.equals("null")) {
					r = "assertNull(" + r + ");";
				} else {
					r = "assertEquals(" + r + ", " + fv + ");";
				}
				asserts.add(r.replace("$this", name));
				return;
			}
		}

		throw new RuntimeException("Invalid attribute found in map file: " + fieldName);
	}

	private static void setObjectField(Map<String, Class<?>> objectMap, List<String> initializers, String name, String fieldName, String fieldValue) {
		// go through class and all of its superclasses
		for (Class<?> reflection = objectMap.get(name); reflection != null; reflection = reflection.getSuperclass()) {
			// search for field
			for (Field field : reflection.getDeclaredFields()) {
				if (!field.getName().equals(fieldName)) continue;
				String fv = deserializeAttribute(objectMap, field, field.getType(), fieldValue);
				String r = "ReflectionUtil.setPrivateField(" + reflection.getSimpleName() + ".class, $this, \"" + fieldName + "\", " + fv + ");";
				initializers.add(r.replace("$this", name));
				return;
			}
		}

		throw new RuntimeException("Invalid attribute found in map file: " + fieldName);
	}

	private static String deserializeAttribute(Map<String, Class<?>> objectMap, Field field, Class<?> type, String attributeValue) {
		// if attribute value is blank, return null
		if (attributeValue.equals("null")) return null;

		// if type is int, return a parsed int
		if (type == int.class) {
			return attributeValue;
		}

		// if type is boolean, return a parsed boolean
		if (type == boolean.class) {
			return attributeValue;
		}

		// if type is list, construct a list, and fill it with the
		// deserialized object of all the serialized elements
		if (type == List.class) {
			Class<?> listType = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
			List<String> list = new ArrayList<>();

			// add all values to the list using the same castAttribute
			attributeValue = attributeValue.substring(1, attributeValue.length() - 1);
			String[] listValues = attributeValue.split(",");
			if (listValues.length == 1 && listValues[0].isBlank()) return "new ArrayList<>()";
			for (String value : listValues) {
				list.add(deserializeAttribute(objectMap, null, listType, value.strip()));
			}
			return list.stream().collect(Collectors.joining(", ", "ReflectionUtil.asList(", ")"));
		}

		// if type is effect, create the corresponding effect
		if (type == Effect.class) {
			return switch (attributeValue) {
			case "None" -> "new NoEffect($this)";
			case "Banana" -> "new BananaEffect($this)";
			case "Technokol" -> "new TechnokolEffect($this)";
			default -> throw new RuntimeException("Unknown effect type: " + attributeValue);
			};
		}

		// if attribute value is found in ModelObjectFactory, return that
		Object obj = objectMap.get(attributeValue);
		if (obj != null) {
			return attributeValue;
		}

		throw new RuntimeException("Unsupported attribute type: " + type.getSimpleName() + ", value: " + attributeValue);
	}
}
