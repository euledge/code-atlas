---
name: code-atlas
description: Generates architectural diagrams (PlantUML, Mermaid) for the Java project using the Code Atlas Gradle plugin. Use when you need to visualize the codebase structure, class relationships, or package dependencies.
---

# Code Atlas

## Overview

This skill utilizes the `io.github.euledge.code-atlas` Gradle plugin to automatically generate architectural diagrams from compiled Java bytecode. It supports PlantUML and Mermaid formats.

## Core Mandate

- **NEVER modify `build.gradle`** to change diagram settings.
- **ALWAYS use command-line options** (e.g., `--rootPackages`, `--formats`) to specify or override configuration.
- **ENSURE the project is built** (`./gradlew classes`) before generating diagrams, as the plugin analyzes bytecode.

## Capabilities

### 1. Help & Discovery

To see all available options directly from the plugin:
```powershell
./gradlew help --task generateDiagrams
```

### 2. Generate Diagrams

Use the `generateDiagrams` task with command-line options.

**Available Options:**
- `--formats`: `plantuml`, `mermaid` (comma-separated, default: `plantuml`).
- `--outputDir`: Directory path for output files (default: `docs/diagrams`).
- `--rootPackages`: Package prefixes to include (e.g., `com.example.domain`).
- `--showDetails`: `true` or `false` (default: `true`). Set to `false` for a simplified overview without fields/methods.
- `--stripPackagePrefix`: Removes prefix from class names for cleaner diagrams (e.g., `com.example`).
- `--groupByPackage`: `true` or `false` (default: `true`). Groups classes into package blocks.

**Example (Simplified Mermaid Diagram):**
```powershell
./gradlew generateDiagrams --rootPackages=com.example.ordertable --formats=mermaid --showDetails=false --stripPackagePrefix=com.example
```

## Workflow

1.  **Build Project**: Run `./gradlew classes` to ensure bytecode is up-to-date.
2.  **Identify Scope**: Determine the package or context to visualize.
3.  **Execute Task**: Run `./gradlew generateDiagrams` with desired options.
4.  **Verify**: Check the `outputDir` (default `docs/diagrams`) for generated `.puml` or `.mmd` files.

## Troubleshooting

- **No classes found**: Run `./gradlew classes` first.
- **Diagram too complex**: Use `--showDetails=false` and `--rootPackages` to narrow the scope.
- **Verbose output**: Run with `--info` to see which classes are being analyzed.

## Example Instructions / 指示の例

- "Generate class diagrams for the `user` context using Mermaid."
  （Mermaidを使って `user` コンテキストのクラス図を生成して。）
- "Create a simplified diagram of the `com.example.domain` package, excluding detailed fields/methods."
  （`com.example.domain` パッケージの簡略化した図を作成して。詳細なフィールドやメソッドは除外して。）
- "Run the help task for generateDiagrams to show available options."
  （generateDiagramsのヘルプタスクを実行して利用可能なオプションを表示して。）
