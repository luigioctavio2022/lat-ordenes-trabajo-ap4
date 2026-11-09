#!/bin/sh
set -eu
cd "$(dirname "$0")"
mkdir -p out
find src/main/java -name '*.java' > fuentes.txt
javac -encoding UTF-8 -d out @fuentes.txt
jar --create --file lat-ap4.jar --main-class lat.Main -C out lat/Main.class -C out lat/modelo -C out lat/persistencia -C out lat/servicio -C out lat/vista
printf '%s\n' 'Compilación terminada. Ejecutar: java -cp "lat-ap4.jar:lib/*" lat.Main'
