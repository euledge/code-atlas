package com.euledge.codeatlas.generator;

import com.euledge.codeatlas.model.ClassNode;
import java.util.Map;

/**
 * An interface for generating diagrams from a map of class nodes.
 * Implementations of this interface are responsible for converting the class
 * structure information into a specific diagram format (e.g., PlantUML, Mermaid).
 */
public interface DiagramGenerator {
    /**
     * Generates a diagram representation as a string.
     *
     * @param classes A map of class names to ClassNode objects representing the classes to be included in the diagram.
     * @param showDetails If true, the generated diagram should include details like fields and methods.
     * @return A string containing the diagram definition in the specific format of the implementation.
     */
    String generate(Map<String, ClassNode> classes, boolean showDetails);
}
