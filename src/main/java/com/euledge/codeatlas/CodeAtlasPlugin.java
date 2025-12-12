package com.euledge.codeatlas;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import java.util.Arrays;
import java.util.List;

/**
 * The main entry point for the CodeAtlas Gradle plugin.
 * This plugin registers the `codeAtlas` extension for configuration and the `generateDiagrams` task
 * for executing the diagram generation.
 */
public class CodeAtlasPlugin implements Plugin<Project> {
    /**
     * Applies the plugin to the given Gradle project.
     * This method creates the `codeAtlas` extension and the `generateDiagrams` task,
     * and configures the task based on project properties or the extension's settings.
     * @param project The project to apply the plugin to.
     */
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

            // Configure rootPackages: Command line > Extension
            Object cmdRootPackages = project.getProperties().get("rootPackages");
            if (cmdRootPackages != null) {
                if (cmdRootPackages instanceof String) {
                    // Assume comma-separated string for list property
                    task.getRootPackages().set(Arrays.asList(((String) cmdRootPackages).split(",")));
                } else if (cmdRootPackages instanceof List) {
                    // If it's already a list
                    task.getRootPackages().set((List<String>) cmdRootPackages);
                }
            } else {
                task.getRootPackages().set(extension.getRootPackages());
            }

            // Configure showDetails: Command line > Extension
            Object cmdShowDetails = project.getProperties().get("showDetails");
            if (cmdShowDetails != null) {
                if (cmdShowDetails instanceof String) {
                    task.getShowDetails().set(Boolean.parseBoolean((String) cmdShowDetails));
                } else if (cmdShowDetails instanceof Boolean) {
                    task.getShowDetails().set((Boolean) cmdShowDetails);
                }
            } else {
                task.getShowDetails().set(extension.getShowDetails());
            }

            // Configure stripPackagePrefix: Command line > Extension
            Object cmdStripPrefix = project.getProperties().get("stripPackagePrefix");
            if (cmdStripPrefix != null && cmdStripPrefix instanceof String) {
                task.getStripPackagePrefix().set((String) cmdStripPrefix);
            } else {
                task.getStripPackagePrefix().set(extension.getStripPackagePrefix());
            }

            // Configure groupByPackage: Command line > Extension
            Object cmdGroupByPackage = project.getProperties().get("groupByPackage");
            if (cmdGroupByPackage != null) {
                if (cmdGroupByPackage instanceof String) {
                    task.getGroupByPackage().set(Boolean.parseBoolean((String) cmdGroupByPackage));
                } else if (cmdGroupByPackage instanceof Boolean) {
                    task.getGroupByPackage().set((Boolean) cmdGroupByPackage);
                }
            } else {
                task.getGroupByPackage().set(extension.getGroupByPackage());
            }
        });
    }
}
