package com.euledge.codeatlas.generator;

import com.euledge.codeatlas.model.ClassNode;
import java.util.List;
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
    public String generate(Map<String, ClassNode> classes, boolean showDetails, String stripPackagePrefix, boolean groupByPackage) {
        StringBuilder sb = new StringBuilder();
        sb.append("@startuml\n");

        if (groupByPackage) {
            // Group classes by package
            Map<String, List<ClassNode>> packageGroups = groupClassesByPackage(classes, stripPackagePrefix);
            
            // Generate package blocks
            for (Map.Entry<String, List<ClassNode>> entry : packageGroups.entrySet()) {
                String packageName = entry.getKey();
                List<ClassNode> classesInPackage = entry.getValue();
                
                sb.append("package ").append(packageName).append(" {\n");
                
                for (ClassNode node : classesInPackage) {
                    String simpleName = getSimpleName(node.getName(), stripPackagePrefix);
                    sb.append("    class ").append(simpleName);
                    if (showDetails && (!node.getFields().isEmpty() || !node.getMethods().isEmpty())) {
                        sb.append(" {\n");
                        node.getFields().forEach(field -> sb.append("        + ").append(field).append("\n"));
                        node.getMethods().forEach(method -> sb.append("        + ").append(method).append("\n"));
                        sb.append("    }\n");
                    } else {
                        sb.append(" {\n    }\n");
                    }
                }
                
                sb.append("}\n\n");
            }
            
            // Generate relationships outside package blocks
            for (ClassNode node : classes.values()) {
                String nodeName = getDisplayName(node.getName(), stripPackagePrefix);
                
                if (node.getSuperClassName() != null && classes.containsKey(node.getSuperClassName())) {
                    String superName = getDisplayName(node.getSuperClassName(), stripPackagePrefix);
                    sb.append(superName).append(" <|-- ").append(nodeName).append("\n");
                }
                
                for (String iface : node.getInterfaces()) {
                    if (classes.containsKey(iface)) {
                        String ifaceName = getDisplayName(iface, stripPackagePrefix);
                        sb.append(ifaceName).append(" <|.. ").append(nodeName).append("\n");
                    }
                }
                
                for (String dep : node.getDependencies()) {
                    if (classes.containsKey(dep)) {
                        String depName = getDisplayName(dep, stripPackagePrefix);
                        sb.append(nodeName).append(" ..> ").append(depName).append("\n");
                    }
                }
            }
        } else {
            // Original implementation with optional prefix stripping
            for (ClassNode node : classes.values()) {
                String className = getDisplayName(node.getName(), stripPackagePrefix);
                sb.append("class ").append(className);
                if (showDetails && (!node.getFields().isEmpty() || !node.getMethods().isEmpty())) {
                    sb.append(" {\n");
                    node.getFields().forEach(field -> sb.append("    + ").append(field).append("\n"));
                    node.getMethods().forEach(method -> sb.append("    + ").append(method).append("\n"));
                    sb.append("}\n");
                } else {
                    sb.append(" {\n}\n");
                }

                if (node.getSuperClassName() != null && classes.containsKey(node.getSuperClassName())) {
                    String superName = getDisplayName(node.getSuperClassName(), stripPackagePrefix);
                    sb.append(superName).append(" <|-- ").append(className).append("\n");
                }

                for (String iface : node.getInterfaces()) {
                    if (classes.containsKey(iface)) {
                        String ifaceName = getDisplayName(iface, stripPackagePrefix);
                        sb.append(ifaceName).append(" <|.. ").append(className).append("\n");
                    }
                }

                for (String dep : node.getDependencies()) {
                    if (classes.containsKey(dep)) {
                        String depName = getDisplayName(dep, stripPackagePrefix);
                        sb.append(className).append(" ..> ").append(depName).append("\n");
                    }
                }
            }
        }

        sb.append("@enduml\n");
        return sb.toString();
    }

    /**
     * Groups classes by their package name.
     */
    private Map<String, List<ClassNode>> groupClassesByPackage(Map<String, ClassNode> classes, String stripPrefix) {
        Map<String, List<ClassNode>> groups = new java.util.LinkedHashMap<>();
        
        for (ClassNode node : classes.values()) {
            String packageName = getPackageName(node.getName(), stripPrefix);
            groups.computeIfAbsent(packageName, k -> new java.util.ArrayList<>()).add(node);
        }
        
        return groups;
    }

    /**
     * Extracts the package name from a fully qualified class name.
     */
    private String getPackageName(String fullName, String stripPrefix) {
        String name = stripPrefix(fullName, stripPrefix);
        int lastDot = name.lastIndexOf('.');
        return lastDot > 0 ? name.substring(0, lastDot) : "";
    }

    /**
     * Extracts the simple class name (without package) from a fully qualified class name.
     */
    private String getSimpleName(String fullName, String stripPrefix) {
        String name = stripPrefix(fullName, stripPrefix);
        int lastDot = name.lastIndexOf('.');
        return lastDot > 0 ? name.substring(lastDot + 1) : name;
    }

    /**
     * Returns the display name (with package but prefix stripped).
     */
    private String getDisplayName(String fullName, String stripPrefix) {
        return stripPrefix(fullName, stripPrefix);
    }

    /**
     * Strips the specified prefix from a class name.
     */
    private String stripPrefix(String className, String prefix) {
        if (prefix != null && !prefix.isEmpty() && className.startsWith(prefix)) {
            return className.substring(prefix.length());
        }
        return className;
    }
}
