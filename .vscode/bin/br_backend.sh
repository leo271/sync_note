#!/bin/sh
cd backend
find . -name "*.class" -type f -delete
javac Main.java -d classes
java -cp classes backend.Main
