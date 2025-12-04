package com.euledge.codeatlas;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class CodeAtlasPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        CodeAtlasExtension extension = project.getExtensions().create("codeAtlas", CodeAtlasExtension.class);

        project.getTasks().create("generateDiagrams", CodeAtlasTask.class, (task) -> {
            task.setGroup("documentation");
            task.setDescription("Generates class diagrams (PlantUML, Mermaid, Draw.io) for the project.");
            task.getFormats().set(extension.getFormats());
            task.getOutputDir().set(extension.getOutputDir());
        });
    }
}
