package com.euledge.codeatlas;

import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;

/**
 * Gradle extension for configuring the CodeAtlas plugin.
 * This allows users to set default values for diagram generation in their `build.gradle` file.
 */
public abstract class CodeAtlasExtension {
    /**
     * Specifies the list of diagram formats to generate (e.g., "plantuml", "mermaid").
     * Defaults to ["plantuml", "mermaid", "drawio"].
     * @return A ListProperty of strings representing the formats.
     */
    public abstract ListProperty<String> getFormats();

    /**
     * Specifies the output directory for the generated diagrams, relative to the project root.
     * Defaults to "build/reports/code-atlas".
     * @return A Property for the output directory path.
     */
    public abstract Property<String> getOutputDir();

    /**
     * Specifies the root package to scan for classes. If empty, all project classes are scanned.
     * Defaults to "".
     * @return A Property for the root package name.
     */
    public abstract Property<String> getRootPackage();

    /**
     * Specifies whether to include detailed information (public fields and methods) in the diagrams.
     * Defaults to false.
     * @return A Property for the showDetails flag.
     */
    public abstract Property<Boolean> getShowDetails();

    /**
     * Constructor that sets the default values for the extension properties.
     */
    public CodeAtlasExtension() {
        getFormats().convention(java.util.Arrays.asList("plantuml", "mermaid"));
        getOutputDir().convention("build/reports/code-atlas");
        getRootPackage().convention("");
        getShowDetails().convention(false);
    }
}
