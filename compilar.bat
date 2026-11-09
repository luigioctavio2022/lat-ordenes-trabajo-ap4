@echo off
cd /d "%~dp0"
if not exist out mkdir out
(for /r src\main\java %%f in (*.java) do @echo "%%f") > fuentes.txt
javac -encoding UTF-8 -d out @fuentes.txt
if errorlevel 1 exit /b 1
jar --create --file lat-ap4.jar --main-class lat.Main -C out lat/Main.class -C out lat/modelo -C out lat/persistencia -C out lat/servicio -C out lat/vista
if errorlevel 1 exit /b 1
echo Ejecutar: java -cp "lat-ap4.jar;lib/*" lat.Main
