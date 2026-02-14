package com.euledge.codeatlas;

import com.euledge.codeatlas.analyzer.ClassAnalyzer;
import com.euledge.codeatlas.generator.DiagramGenerator;
import com.euledge.codeatlas.generator.MermaidGenerator;
import com.euledge.codeatlas.generator.PlantUmlGenerator;
import com.euledge.codeatlas.model.ClassNode;
import org.gradle.api.DefaultTask;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.options.Option;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The main Gradle task for CodeAtlas.
 * This task orchestrates the class analysis and diagram generation process based on the configured properties.
 */
public abstract class CodeAtlasTask extends DefaultTask {

    /**
     * The list of diagram formats to generate (e.g., "plantuml", "mermaid").
     * @return A ListProperty of strings representing the formats.
     */
    @Input
    public abstract ListProperty<String> getFormats();

    @Option(option = "formats", description = "Specifies the diagram formats to generate (e.g., 'plantuml', 'mermaid').")
    public void setFormatsOption(List<String> formats) {
        getFormats().set(formats);
    }

    /**
     * The directory where the generated diagrams will be saved.
     * @return A Property for the output directory path.
     */
    @OutputDirectory
    public abstract Property<String> getOutputDir();

    @Option(option = "outputDir", description = "Specifies the output directory for the generated diagrams, relative to the project root.")
    public void setOutputDirOption(String outputDir) {
        getOutputDir().set(outputDir);
    }

    /**
     * The root packages to scan for classes. If empty, all project classes are scanned.
     * @return A ListProperty for the root package names.
     */
    @Input
    public abstract ListProperty<String> getRootPackages();

    @Option(option = "rootPackages", description = "Specifies the root packages to scan for classes. If empty, all project classes are scanned.")
    public void setRootPackagesOption(List<String> rootPackages) {
        getRootPackages().set(rootPackages);
    }

    /**
     * Whether to include detailed information (public fields and methods) in the diagrams.
     * @return A Property for the showDetails flag.
     */
    @Input
    public abstract Property<Boolean> getShowDetails();

    @Option(option = "showDetails", description = "Whether to include detailed information (public fields and methods) in the diagrams.")
    public void setShowDetailsOption(String showDetails) {
        getShowDetails().set(Boolean.parseBoolean(showDetails));
    }

    /**
     * The package prefix to strip from class names in diagrams.
     * @return A Property for the package prefix to strip.
     */
    @Input
    public abstract Property<String> getStripPackagePrefix();

    @Option(option = "stripPackagePrefix", description = "Specifies the package prefix to strip from class names in diagrams.")
    public void setStripPackagePrefixOption(String stripPackagePrefix) {
        getStripPackagePrefix().set(stripPackagePrefix);
    }

    /**
     * Whether to group classes by package in the diagrams.
     * @return A Property for the groupByPackage flag.
     */
    @Input
    public abstract Property<Boolean> getGroupByPackage();

    @Option(option = "groupByPackage", description = "Whether to group classes by package in the diagrams.")
    public void setGroupByPackageOption(String groupByPackage) {
        getGroupByPackage().set(Boolean.parseBoolean(groupByPackage));
    }

    /**
     * The main action for the task. It performs class analysis and triggers the generation
     * of diagrams for the configured formats.
     */
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
        List<String> rootPackages = getRootPackages().get();
        boolean showDetails = getShowDetails().get();
        ClassAnalyzer analyzer = new ClassAnalyzer();
        Map<String, ClassNode> classes = analyzer.analyze(classpath, rootPackages, showDetails);
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
                generateFormat(format, classes, outputDir, showDetails);
            } catch (IOException e) {
                getLogger().error("Failed to generate " + format + " diagram", e);
            }
        }
    }

    /**
     * Generates and writes a diagram file for a specific format.
     *
     * @param format The diagram format to generate (e.g., "plantuml").
     * @param classes The map of analyzed class nodes.
     * @param outputDir The directory to write the file to.
     * @param showDetails Flag to include class details.
     * @throws IOException if an I/O error occurs during file writing.
     */
    private void generateFormat(String format, Map<String, ClassNode> classes, File outputDir, boolean showDetails) throws IOException {
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
            default:
                getLogger().warn("Unknown format: " + format);
                return;
        }

        String content = generator.generate(classes, showDetails, 
                getStripPackagePrefix().get(), getGroupByPackage().get());
        File file = new File(outputDir, "class-diagram." + extension);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        }
        getLogger().lifecycle("Generated " + file.getAbsolutePath());
    }
}
