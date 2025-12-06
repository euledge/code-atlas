package com.euledge.codeatlas.generator;

import com.euledge.codeatlas.model.ClassNode;
import java.util.Map;

/**
 * A {@link DiagramGenerator} for creating class diagrams in PlantUML syntax.
 * This generator converts the class structure information into a string
 * that can be rendered by PlantUML.
 */
public class PlantUmlGenerator implements DiagramGenerator {
    /**
     * {@inheritDoc}
     *
     * This implementation generates a class diagram in PlantUML format.
     * The output string is enclosed in {@code @startuml} and {@code @enduml} tags.
     */
    @Override
    public String generate(Map<String, ClassNode> classes, boolean showDetails) {
        StringBuilder sb = new StringBuilder();
        sb.append("@startuml\n");

        for (ClassNode node : classes.values()) {
            sb.append("class ").append(node.getName());
            if (showDetails && (!node.getFields().isEmpty() || !node.getMethods().isEmpty())) {
                sb.append(" {\n");
                node.getFields().forEach(field -> sb.append("    + ").append(field).append("\n"));
                node.getMethods().forEach(method -> sb.append("    + ").append(method).append("\n"));
                sb.append("}\n");
            } else {
                sb.append(" {\n}\n");
            }

            if (node.getSuperClassName() != null && classes.containsKey(node.getSuperClassName())) {
                sb.append(node.getSuperClassName()).append(" <|-- ").append(node.getName()).append("\n");
            }

            for (String iface : node.getInterfaces()) {
                if (classes.containsKey(iface)) {
                    sb.append(iface).append(" <|.. ").append(node.getName()).append("\n");
                }
            }

            for (String dep : node.getDependencies()) {
                if (classes.containsKey(dep)) {
                    sb.append(node.getName()).append(" ..> ").append(dep).append("\n");
                }
            }
        }

        sb.append("@enduml\n");
        return sb.toString();
    }
}
