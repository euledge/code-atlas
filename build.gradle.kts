plugins {
    `java-gradle-plugin`
    `maven-publish`
    id("com.gradle.plugin-publish") version "1.2.1"
}

group = "io.github.euledge"
version = "0.9.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.classgraph:classgraph:4.8.165")
}

gradlePlugin {
    website.set("https://github.com/euledge/code-atlas")
    vcsUrl.set("https://github.com/euledge/code-atlas.git")

    plugins {
        register("code-atlas") {
            id = "io.github.euledge.code-atlas"
            implementationClass = "com.euledge.codeatlas.CodeAtlasPlugin"

            displayName = "Code Atlas Gradle Plugin"
            description = "A Gradle plugin to analyze compiled Java classes and generate class diagrams in PlantUML and Mermaid formats."
            tags.set(listOf(
                "diagram",
                "dependencies",
                "architecture",
                "visualization",
                "plantuml",
                "mermaid",
                "uml"
            ))
        }
    }
}
