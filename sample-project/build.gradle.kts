plugins {
    java
    id("io.github.euledge.code-atlas") version "1.0.0"
}

repositories {
    mavenLocal()
    mavenCentral()
}

codeAtlas {
    formats.set(listOf("plantuml", "mermaid"))
    outputDir.set("docs/diagrams")
    showDetails.set(true)
}

