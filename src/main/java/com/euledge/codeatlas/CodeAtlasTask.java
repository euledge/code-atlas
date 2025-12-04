package com.euledge.codeatlas;

import com.euledge.codeatlas.analyzer.ClassAnalyzer;
import com.euledge.codeatlas.generator.DiagramGenerator;
import com.euledge.codeatlas.generator.DrawIoGenerator;
import com.euledge.codeatlas.generator.MermaidGenerator;
import com.euledge.codeatlas.generator.PlantUmlGenerator;
import com.euledge.codeatlas.model.ClassNode;
import org.gradle.api.DefaultTask;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class CodeAtlasTask extends DefaultTask {

    @Input
    public abstract ListProperty<String> getFormats();

    @OutputDirectory
    public abstract Property<String> getOutputDir();

    @TaskAction
    public void generate() {
        getLogger().lifecycle("Analyzing project classes...");

        // Prepare classpath for analysis
        Set<File> classpath = new HashSet<>();
        // Add compiled classes
        File classesDir = new File(getProject().getLayout().getBuildDirectory().get().getAsFile(), "classes/java/main");
        if (classesDir.exists()) {
            classpath.add(classesDir);
            getLogger().lifecycle("Found classes directory: " + classesDir.getAbsolutePath());
        } else {
            getLogger().warn("Warning: No compiled classes found at " + classesDir.getAbsolutePath());
            return;
        }

        // Analyze
        ClassAnalyzer analyzer = new ClassAnalyzer();
        Map<String, ClassNode> classes = analyzer.analyze(classpath);
        getLogger().lifecycle("Found " + classes.size() + " classes.");

        if (classes.isEmpty()) {
            getLogger().warn("No classes found to analyze. Skipping diagram generation.");
            return;
        }

        File outputDir = new File(getProject().getProjectDir(), getOutputDir().get());
        if (!outputDir.exists()) {
            boolean created = outputDir.mkdirs();
            getLogger().lifecycle(
                    "Created output directory: " + outputDir.getAbsolutePath() + " (success: " + created + ")");
        }

        for (String format : getFormats().get()) {
            try {
                generateFormat(format, classes, outputDir);
            } catch (IOException e) {
                getLogger().error("Failed to generate " + format + " diagram", e);
            }
        }
    }

    private void generateFormat(String format, Map<String, ClassNode> classes, File outputDir) throws IOException {
        DiagramGenerator generator;
        String extension;

        switch (format.toLowerCase()) {
            case "plantuml":
                generator = new PlantUmlGenerator();
                extension = "puml";
                break;
            case "mermaid":
                generator = new MermaidGenerator();
                extension = "mmd";
                break;
            case "drawio":
                generator = new DrawIoGenerator();
                extension = "drawio";
                break;
            default:
                getLogger().warn("Unknown format: " + format);
                return;
        }

        String content = generator.generate(classes);
        File file = new File(outputDir, "class-diagram." + extension);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        }
        getLogger().lifecycle("Generated " + file.getAbsolutePath());
    }
}
