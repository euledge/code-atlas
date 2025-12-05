plugins {
    `java-gradle-plugin`
    `maven-publish`
}

group = "com.euledge.codeatlas"
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
            id = "com.euledge.codeatlas"
            implementationClass = "com.euledge.codeatlas.CodeAtlasPlugin"
        }
    }
}
