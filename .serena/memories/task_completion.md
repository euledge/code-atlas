# Task Completion Guidelines

When a development task (e.g., bug fix, feature addition) is completed, the following steps should be performed to ensure code quality and integration.

1.  **Build the project**: Verify that the code compiles and passes all unit tests.
    ```sh
    ./gradlew clean build
    ```

2.  **Test with the sample project**: If the changes affect the plugin's behavior, it's crucial to test it against the `sample-project`.
    
    First, publish the updated plugin to your local repository:
    ```sh
    ./gradlew publishToMavenLocal
    ```
    
    Then, run the diagram generation in the sample project to confirm the output is as expected:
    ```sh
    cd sample-project
    ../gradlew generateDiagrams
    ```
    
    Inspect the generated diagrams in `sample-project/docs/diagrams`.
