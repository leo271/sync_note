#!/bin/sh
cd backend
find . -name "*.class" -type f -delete
java -cp classes:../libs/* Main.java
