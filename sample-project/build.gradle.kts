plugins {
    java
    id("com.example.codeatlas") version "1.0-SNAPSHOT"
}

repositories {
    mavenCentral()
}

codeAtlas {
    formats.set(listOf("plantuml", "mermaid", "drawio"))
    outputDir.set("docs/diagrams")
}
