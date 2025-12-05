package com.euledge.codeatlas.analyzer;

import com.euledge.codeatlas.model.ClassNode;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ClassAnalyzer {

    public Map<String, ClassNode> analyze(Set<File> classpath, String rootPackage, boolean showDetails) {
        Map<String, ClassNode> classMap = new HashMap<>();

        ClassGraph classGraph = new ClassGraph()
                .overrideClasspath(classpath)
                .enableAllInfo();

        if (rootPackage != null && !rootPackage.isEmpty()) {
            classGraph.acceptPackages(rootPackage);
        }

        try (ScanResult scanResult = classGraph
                .enableInterClassDependencies()
                .enableInterClassDependencies()
                .scan()) {

            ClassInfoList allClasses = scanResult.getAllClasses();

            for (ClassInfo classInfo : allClasses) {
                if (classInfo.isExternalClass())
                    continue; // Skip external classes for now

                String className = classInfo.getName();
                ClassNode node = new ClassNode(className);

                // Superclass
                ClassInfo superclass = classInfo.getSuperclass();
                if (superclass != null && !superclass.getName().equals("java.lang.Object")) {
                    node.setSuperClassName(superclass.getName());
                }

                // Interfaces
                for (ClassInfo iface : classInfo.getInterfaces()) {
                    node.addInterface(iface.getName());
                }

                if (showDetails) {
                    boolean detailsCaptured = false;
                    try {
                        Class<?> clazz = classInfo.loadClass();
                        Arrays.stream(clazz.getDeclaredFields())
                                .filter(field -> Modifier.isPublic(field.getModifiers()))
                                .filter(field -> field.getDeclaringClass().equals(clazz))
                                .forEach(field -> node.addField(field.getType().getSimpleName() + " " + field.getName()));

                        Arrays.stream(clazz.getDeclaredMethods())
                                .filter(method -> Modifier.isPublic(method.getModifiers()))
                                .filter(method -> method.getDeclaringClass().equals(clazz))
                                .filter(method -> !method.isSynthetic())
                                .forEach(method -> {
                                    String params = Arrays.stream(method.getParameters())
                                            .map(param -> param.getType().getSimpleName())
                                            .collect(Collectors.joining(", "));
                                    node.addMethod(method.getReturnType().getSimpleName() + " " + method.getName() + "(" + params + ")");
                                });
                        detailsCaptured = true;
                        System.out.println("[CodeAtlas] Successfully collected public members via reflection for " + className);
                    } catch (Throwable throwable) {
                        System.out.println("[CodeAtlas] Reflection-based member collection failed for " + className + " (" + throwable.getClass().getSimpleName() + ")");
                    }

                    if (!detailsCaptured) {
                        System.out.println("[CodeAtlas] Falling back to ClassGraph metadata for " + className);
                        classInfo.getFieldInfo().forEach(field -> {
                            if (field.isPublic()) {
                                String fieldType = formatType(field.getTypeSignatureOrTypeDescriptor() != null
                                        ? field.getTypeSignatureOrTypeDescriptor().toString()
                                        : null);
                                node.addField(fieldType + " " + field.getName());
                            }
                        });

                        classInfo.getMethodInfo().forEach(method -> {
                            if (method.isPublic() && !method.isConstructor() && !method.getName().startsWith("<")) {
                                String signature = method.getTypeSignatureOrTypeDescriptor() != null
                                        ? method.getTypeSignatureOrTypeDescriptor().toString()
                                        : "()void";
                                node.addMethod(method.getName() + signature);
                            }
                        });
                    }
                }
                // Using getClassDependencies() for a broader dependency graph
                // This includes fields, method params, return types, etc.
                for (ClassInfo dep : classInfo.getClassDependencies()) {
                    if (!dep.getName().startsWith("java.") && !dep.getName().equals(className)) {
                        node.addDependency(dep.getName());
                    }
                }

                classMap.put(className, node);
            }
        }
        return classMap;
    }
    private String formatType(String typeDescriptor) {
        if (typeDescriptor == null) {
            return "void";
        }
        // Remove array indicators (e.g., [Ljava/lang/String; -> Ljava/lang/String;)
        typeDescriptor = typeDescriptor.replaceAll("\\[+", "");
        
        // Convert internal class names (Lcom/example/Class;) to simple names (com.example.Class)
        if (typeDescriptor.startsWith("L") && typeDescriptor.endsWith(";")) {
            typeDescriptor = typeDescriptor.substring(1, typeDescriptor.length() - 1).replaceAll("/", ".");
        }
        
        // Handle primitive types (I, Z, V, etc.)
        switch (typeDescriptor) {
            case "I": return "int";
            case "Z": return "boolean";
            case "V": return "void";
            case "J": return "long";
            case "D": return "double";
            case "F": return "float";
            case "B": return "byte";
            case "C": return "char";
            case "S": return "short";
            default:
                // Use simple name if it's a fully qualified name
                int lastDot = typeDescriptor.lastIndexOf('.');
                return lastDot > 0 ? typeDescriptor.substring(lastDot + 1) : typeDescriptor;
        }
    }
}
