# Suggested Commands

## Build and Test

The main command to build the project, run tests, and install the plugin to the local Maven repository. This is the primary command used in the CI workflow.

```sh
./gradlew clean build publishToMavenLocal
```

- `clean`: Deletes the `build` directory.
- `build`: Compiles the code, runs unit tests, and assembles the plugin JAR.
- `publishToMavenLocal`: Publishes the plugin artifact to the local Maven repository (`~/.m2/repository`), which is necessary for testing the plugin with the `sample-project`.

## Generate Diagrams (for the sample project)

To test the plugin's functionality, you can run the `generateDiagrams` task within the `sample-project`.

First, ensure you have published the plugin locally:
```sh
./gradlew publishToMavenLocal
```

Then, run the diagram generation task:
```sh
cd sample-project
../gradlew generateDiagrams
```
The output will be in `sample-project/docs/diagrams`.

## Linting and Formatting

There are no dedicated linting or formatting tasks configured in the `build.gradle.kts` file. The `build` task will fail if there are compilation errors.
