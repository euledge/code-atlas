package com.euledge.codeatlas.generator;

import com.euledge.codeatlas.model.ClassNode;
import java.util.Map;

public class PlantUmlGenerator implements DiagramGenerator {
    @Override
    public String generate(Map<String, ClassNode> classes) {
        StringBuilder sb = new StringBuilder();
        sb.append("@startuml\n");

        for (ClassNode node : classes.values()) {
            sb.append("class ").append(node.getName()).append(" {\n");
            sb.append("}\n");

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
