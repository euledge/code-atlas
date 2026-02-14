# Codebase Structure

The project follows a standard Gradle plugin structure. The source code is located in `src/main/java`.

The main package is `com.euledge.codeatlas`.

- **`com.euledge.codeatlas`**: The root package.
    - `CodeAtlasPlugin.java`: The main plugin entry point, implementing `org.gradle.api.Plugin`. It registers the extension and the task.
    - `CodeAtlasTask.java`: The core task logic for `generateDiagrams`. It orchestrates the analysis and diagram generation.
    - `CodeAtlasExtension.java`: Defines the configuration block `codeAtlas { ... }` for the plugin in `build.gradle.kts`.

- **`com.euledge.codeatlas.analyzer`**:
    - `ClassAnalyzer.java`: Contains the logic for scanning the classpath using the ClassGraph library. It builds a model of classes and their relationships (dependencies, inheritance).

- **`com.euledge.codeatlas.generator`**:
    - `DiagramGenerator.java`: An interface for diagram generation.
    - `MermaidGenerator.java`: Implementation that generates diagrams in Mermaid format.
    - `PlantUmlGenerator.java`: Implementation that generates diagrams in PlantUML format.

- **`com.euledge.codeatlas.model`**:
    - `ClassNode.java`: A data class (POJO) representing a single class found during analysis. It stores information like class name, fields, methods, and relationships to other classes.
