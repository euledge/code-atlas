plugins {
    `java-gradle-plugin`
    `maven-publish`
}

group = "com.example.codeatlas"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.classgraph:classgraph:4.8.165")
}

gradlePlugin {
    plugins {
        create("codeAtlas") {
            id = "com.example.codeatlas"
            implementationClass = "com.example.codeatlas.CodeAtlasPlugin"
        }
    }
}
