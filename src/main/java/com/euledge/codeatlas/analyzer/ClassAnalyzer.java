package com.example.codeatlas.analyzer;

import com.example.codeatlas.model.ClassNode;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClassAnalyzer {

    public Map<String, ClassNode> analyze(Set<File> classpath) {
        Map<String, ClassNode> classMap = new HashMap<>();

        try (ScanResult scanResult = new ClassGraph()
                .overrideClasspath(classpath)
                .enableAllInfo()
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

                // Fields (Dependencies)
                // Note: This is a simplified dependency extraction based on field types.
                classInfo.getFieldInfo().forEach(field -> {
                    String typeName = field.getTypeDescriptor().toString();
                    // Need to parse type descriptor or use getTypeSignature() if available/simpler
                    // For simplicity in this version, let's try to get the class name directly if
                    // possible
                    // ClassGraph provides TypeSignature, but let's stick to simple relation for
                    // now.
                    // Actually, ClassInfo has getFieldInfo() which returns FieldInfo.
                    // FieldInfo.getTypeDescriptor() returns TypeSignature.

                    // Let's use a simpler approach: check referenced classes
                    // getClassDependencies() might be too broad (includes method bodies), but let's
                    // see.
                });

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
}
