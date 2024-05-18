package macaroni.test.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class ReflectionUtil {
	public static void setPrivateField(Class<?> clazz, Object instance, String fieldName, Object value) {
		assertDoesNotThrow(() -> {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			f.set(instance, value);
		});
	}

	@SuppressWarnings("unchecked")
	public static <T> T getPrivateField(Class<?> clazz, Object instance, String fieldName) {
		return (T) assertDoesNotThrow(() -> {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.get(instance);
		});
	}

	public static Object asList(Object... params) {
		return new ArrayList<>(Arrays.asList(params));
	}
}
