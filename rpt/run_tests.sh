#!/bin/bash

# Check if JUnit JARs are available
if [ ! -f "lib/junit-4.13.2.jar" ] || [ ! -f "lib/hamcrest-core-1.3.jar" ]; then
  echo "JUnit JARs not found. Please download them to the lib directory:"
  echo "mkdir -p lib"
  echo "cd lib"
  echo "Download junit-4.13.2.jar and hamcrest-core-1.3.jar to this directory"
  echo "You can get them from Maven repository:"
  echo "junit-4.13.2.jar: https://repo1.maven.org/maven2/junit/junit/4.13.2/junit-4.13.2.jar"
  echo "hamcrest-core-1.3.jar: https://repo1.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar"
  exit 1
fi

# Create build directory
mkdir -p build

# Compile the source files
echo "Compiling source files..."
javac -d build src/*.java

# Compile the test files
echo "Compiling test files..."
javac -cp build:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar -d build test/*.java

# Run the tests
echo "Running tests..."
java -cp build:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar vise.tool.AllTests