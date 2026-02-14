# Project Overview

## Purpose

This project, "Code Atlas", is a Gradle plugin designed to analyze a project's compiled Java classes and generate class diagrams. It helps visualize the architecture, dependencies, and inheritance/interface implementations.

The plugin supports generating diagrams in two formats:
- **PlantUML** (`.puml`)
- **Mermaid** (`.mmd`)

## Tech Stack

- **Language**: Java
- **Build Tool**: Gradle (version 8.5 or higher is required)
- **JDK**: Java 21 is required for the build and test environment.
- **Key Libraries**:
    - `io.github.classgraph`: Used for classpath scanning to analyze class structures and dependencies.
