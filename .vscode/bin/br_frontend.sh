#!/bin/sh
cd frontend
find . -name "*.class" -type f -delete
javac Main.java -d classes -cp ../libs/sqlite-jdbc.jar
java -cp classes:../libs/* backend.Main
