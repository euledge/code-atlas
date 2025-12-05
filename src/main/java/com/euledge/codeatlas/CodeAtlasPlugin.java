package com.euledge.codeatlas;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import java.util.Arrays;
import java.util.List;

public class CodeAtlasPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        CodeAtlasExtension extension = project.getExtensions().create("codeAtlas", CodeAtlasExtension.class);

        project.getTasks().create("generateDiagrams", CodeAtlasTask.class, (task) -> {
            task.setGroup("documentation");
            task.setDescription("Generates class diagrams (PlantUML, Mermaid, Draw.io) for the project.");

            // Configure formats: Command line > Extension
            Object cmdFormats = project.getProperties().get("formats");
            if (cmdFormats != null) {
                if (cmdFormats instanceof String) {
                    // Assume comma-separated string for list property
                    task.getFormats().set(Arrays.asList(((String) cmdFormats).split(",")));
                } else if (cmdFormats instanceof List) {
                    // If it's already a list
                    task.getFormats().set((List<String>) cmdFormats);
                }
            } else {
                task.getFormats().set(extension.getFormats());
            }

            // Configure outputDir: Command line > Extension
            Object cmdOutputDir = project.getProperties().get("outputDir");
            if (cmdOutputDir != null && cmdOutputDir instanceof String) {
                task.getOutputDir().set((String) cmdOutputDir);
            } else {
                task.getOutputDir().set(extension.getOutputDir());
            }
        });
    }
}
