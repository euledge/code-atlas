plugins {
    java
    id("io.github.euledge.code-atlas") version "1.1.0"
}

repositories {
    mavenLocal()
    mavenCentral()
}

codeAtlas {
    formats.set(listOf("plantuml", "mermaid"))
    rootPackages.set(listOf("com.example.sample","com.example.dummy"))
    outputDir.set("docs/diagrams")
    showDetails.set(true)
    stripPackagePrefix.set("com.example.")
    groupByPackage.set(true)
}


