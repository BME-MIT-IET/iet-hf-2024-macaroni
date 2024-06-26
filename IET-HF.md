# A projekt célja
A kiinduló projekt a negyedik félévi szoftver projekt laboratórium beadandója. Az alkalmazás egy lokálisan többszemélyes turn-based játék. A játékban két csapat játszik egymás ellen, a szerelőknek hegyi forrásokból kell a vizet eljuttatni a környéki városokba, a szabotőröknek pedig az a célja, hogy minél több víz folyjon el a sivatagba.


Az alkalmazás rendelkezik ablakos és parancssoros megjelenítési opciókkal is. A modell helyes működését egyénileg készített tesztek ellenőrizték, melyeket át készülünk írni Unit-tesztekre.

Emellett szeretnénk automatikus gradle build rendszert is beállítani, hogy a projekt könnyen fordítható legyen, és a tesztek is könnyedén futtathatóak legyenek. Github Actions-t is be szeretnénk konfigurálni, hogy a tesztek automatikusan lefussanak minden pull request esetén.

Ezenkívül UI testing-et, és exploratory testing-et szeretnénk végezni, hogy a felhasználói felületen és a játékmenetben levő esetleges hibákat felderítsük és kijavítsuk.

### Feladatok dokumentációja:

[Build, CI](/doc/build-ci.md)

[Unit Testing](/doc/unit-testing.md)

[UI Testing](/doc/ui-testing.md)

[Exploratory Testing](/doc/exploratory-testing.md)
