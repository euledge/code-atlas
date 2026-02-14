# Code Style and Conventions

Based on an analysis of the existing source code (e.g., `CodeAtlasPlugin.java`), the following conventions are used.

## Formatting
- **Indentation**: 4 spaces.
- **Braces**: Opening braces (`{`) are placed on the same line as the class or method declaration.

## Naming
- **Classes**: `UpperCamelCase` (e.g., `CodeAtlasPlugin`).
- **Methods**: `lowerCamelCase` (e.g., `apply`).
- **Variables**: `lowerCamelCase` (e.g., `cmdFormats`).
- **Constants**: No constants were observed, but standard Java conventions (`UPPER_SNAKE_CASE`) should be assumed.

## Comments
- **Javadoc**: Public classes and methods are documented using Javadoc comments (`/** ... */`).
- **Inline Comments**: Single-line comments (`//`) are used to explain specific implementation details.

## Other
- **Annotations**: `@Override` is used where applicable.
- **`final` keyword**: The `final` keyword is not consistently used for variables or parameters.
- **Language Features**: The code uses modern Java features, including lambda expressions.
