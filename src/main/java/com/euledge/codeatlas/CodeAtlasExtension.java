package com.example.codeatlas;

import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;

public abstract class CodeAtlasExtension {
    public abstract ListProperty<String> getFormats();

    public abstract Property<String> getOutputDir();

    public CodeAtlasExtension() {
        getFormats().convention(java.util.Arrays.asList("plantuml", "mermaid", "drawio"));
        getOutputDir().convention("build/reports/code-atlas");
    }
}
