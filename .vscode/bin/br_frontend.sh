#!/bin/sh
cd frontend
find . -name \"*.class\" -type f -delete
javac Main.java -d classes
java -cp classes Main
