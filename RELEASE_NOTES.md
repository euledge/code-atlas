# Release Notes

## Version 1.0.0 (Upcoming)

**Release Date**: TBD (After PR #4 merge)

**GitHub**: [Pull Request #4](https://github.com/euledge/code-atlas/pull/4)

### ⚠️ Breaking Changes

#### API Change: `rootPackage` → `rootPackages`

The plugin now supports multiple root packages for cross-package dependency analysis.

**Migration Required:**

```kotlin
// ❌ Old (v0.9.0)
codeAtlas {
    rootPackage.set("com.example")
}

// ✅ New (v1.0.0)
codeAtlas {
    rootPackages.set(listOf("com.example"))
}
```

### ✨ New Features

#### Multiple Root Packages Support

Specify multiple package prefixes to analyze dependencies across different architectural layers.

**Use Case: DDD Architecture**
```kotlin
codeAtlas {
    rootPackages.set(listOf(
        "com.example.domain",
        "com.example.infrastructure",
        "com.example.application"
    ))
}
```

**Command Line:**
```sh
./gradlew generateDiagrams \
    --project-prop rootPackages=com.example.domain,com.example.infrastructure
```

**Benefits:**
- 🏗️ Visualize cross-layer dependencies (domain ↔ infrastructure)
- 📦 Analyze dependencies across multiple modules
- 🎯 Fine-grained control over which packages to include
- 📊 More focused diagrams by excluding irrelevant packages

### 🔧 Changes

#### Core Implementation

1. **CodeAtlasExtension.java**
   - Changed: `Property<String> getRootPackage()` → `ListProperty<String> getRootPackages()`
   - Default: Empty list (scans all packages)

2. **CodeAtlasPlugin.java**
   - Added: Comma-separated package list support
   - Handles both String and List input types

3. **CodeAtlasTask.java**
   - Updated: Passes `List<String>` to ClassAnalyzer
   - Added: `List` import

4. **ClassAnalyzer.java**
   - Updated: Accepts `List<String> rootPackages` parameter
   - Uses: `acceptPackages(String...)` with array conversion

#### Documentation

5. **README.md**
   - Updated: All examples to use `rootPackages`
   - Added: DDD architecture use case examples
   - Updated: Command line parameter documentation

6. **README_JA.md**
   - Updated: Japanese version with same changes

### 📦 Version Information

- **Previous Version**: 0.9.0
- **Current Version**: 1.0.0
- **Version Bump Reason**: Breaking API change

### 🚀 Upgrade Guide

#### Step 1: Update Plugin Version

```kotlin
plugins {
    id("io.github.euledge.code-atlas") version "1.0.0"
}
```

#### Step 2: Update Configuration

```kotlin
codeAtlas {
    // Change from:
    // rootPackage.set("com.example")
    
    // To:
    rootPackages.set(listOf("com.example"))
}
```

#### Step 3: Test

```sh
./gradlew generateDiagrams
```

### 📝 Related Issues

- Closes [#3](https://github.com/euledge/code-atlas/issues/3): Feature Request - Support Multiple Root Packages

### 🙏 Acknowledgments

Thank you to all contributors and users who provided feedback for this release!

---

## Version 0.9.0

Initial public release with basic functionality:
- PlantUML diagram generation
- Mermaid diagram generation
- Single root package filtering
- Command line configuration support
