# Code Atlas Gradle Plugin
README [English](README.md) [Japanese](README_JA.md)
 
## Overview

The **Code Atlas** Gradle plugin analyzes compiled Java classes of a project and generates class diagrams in three formats:
- **PlantUML** (`.puml`)
- **Mermaid** (`.mmd`)
- **Draw.io** (`.drawio`)

It is useful for visualising architecture, dependencies, inheritance and interface implementations.

## Usage

1. **Apply the plugin** in your `build.gradle.kts` (or `build.gradle`):
   ```kotlin
   plugins {
       id("com.example.codeatlas") version "1.0.0"
   }
   ```
2. **Configure the extension** (optional):
   ```kotlin
   codeAtlas {
       formats.set(listOf("plantuml", "mermaid", "drawio"))
       outputDir.set("docs/diagrams")
   }
   ```
   - `formats` – list of diagram formats to generate.
   - `outputDir` – directory where the diagram files will be written.
3. **Run the task**:
   ```sh
   ./gradlew generateDiagrams
   ```
   The task will compile the project (if needed), scan the compiled classes and create the diagram files under the configured output directory.

## Sample Project

A minimal sample project is provided under `sample-project`. After publishing the plugin to your local Maven repository (`./gradlew publishToMavenLocal`), you can run:
```sh
cd sample-project
../gradlew generateDiagrams
```
The generated diagrams will be placed in `sample-project/docs/diagrams`.

## Requirements

- Java 21 (or compatible JDK)
- Gradle 8.5 or newer
- The plugin uses **ClassGraph** for classpath scanning.

## License

[MIT License](LICENSE.md)
