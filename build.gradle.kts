plugins {
    `java-gradle-plugin`
    `maven-publish`
    id("com.gradle.plugin-publish") version "1.2.1"
}

group = "com.euledge.codeatlas"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.classgraph:classgraph:4.8.165")
}

gradlePlugin {
    plugins {
        create("codeAtlas") {
            id = "com.euledge.codeatlas"
            implementationClass = "com.euledge.codeatlas.CodeAtlasPlugin"
        }
    }
}

pluginBundle {
    website = "https://github.com/euledge/code-atlas"
    vcsUrl = "https://github.com/euledge/code-atlas.git"
    tags = listOf("java", "gradle", "plugin", "diagram", "mermaid", "plantuml")
    description = "A Gradle plugin to analyze compiled Java classes and generate class diagrams in PlantUML and Mermaid formats."
    plugins {
        "codeAtlas" {
            displayName = "Code Atlas Gradle Plugin"
        }
    }
}
