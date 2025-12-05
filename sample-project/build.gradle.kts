plugins {
    java
    id("com.euledge.codeatlas") version "1.0-SNAPSHOT"
}

repositories {
    mavenCentral()
}

codeAtlas {
    formats.set(listOf("plantuml", "mermaid"))
    outputDir.set("docs/diagrams")
}
