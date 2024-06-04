#!/bin/sh
cd frontend
find . -name \"*.class\" -type f -delete
java -cp classes:../libs/* Main.java
