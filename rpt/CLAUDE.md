# CLAUDE.md - Guidelines for Vise Codebase

## Build/Test Commands
* No formal build system detected (no pom.xml or build.gradle)
* To compile: `javac -d bin src/*.java`
* To run: `java -cp bin Vise`
* To run tests: Not determined (no test files found)

## Code Style Guidelines
* Use **PascalCase** for class names (e.g., `DefaultViseMechanics`)
* Use **camelCase** for method/variable names (e.g., `openSection`, `grippedValues`)
* Use **SNAKE_CASE_CAPS** for constants (e.g., `FILE_SUFFIX`)
* Opening braces on same line as declarations
* Consistent indentation with tabs
* No trailing whitespace

## Error Handling
* Use custom `ViseException` (extends `RuntimeException`) for errors
* Provide descriptive error messages focusing on the specific failure
* Don't catch exceptions unless you can handle them properly

## Project Structure
Vise is a capture-replay testing utility with state pattern implementation:
* Records ("grips") values during recording phase
* Stores values in .vise files
* Compares ("checks") values during checking phase
* Uses state pattern for different operational modes