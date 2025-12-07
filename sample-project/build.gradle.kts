plugins {
    java
    id("com.euledge.codeatlas") version "1.0.0"
}

repositories {
    mavenCentral()
}

codeAtlas {
    formats.set(listOf("plantuml", "mermaid"))
    outputDir.set("docs/diagrams")
    showDetails.set(true)
}
