package com.euledge.codeatlas.model;

import java.util.HashSet;
import java.util.Set;

public class ClassNode {
    private String name;
    private String superClassName;
    private Set<String> interfaces = new HashSet<>();
    private Set<String> dependencies = new HashSet<>();
    private Set<String> fields = new HashSet<>();
    private Set<String> methods = new HashSet<>();

    public ClassNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getSuperClassName() {
        return superClassName;
    }

    public void setSuperClassName(String superClassName) {
        this.superClassName = superClassName;
    }

    public Set<String> getInterfaces() {
        return interfaces;
    }

    public void addInterface(String interfaceName) {
        this.interfaces.add(interfaceName);
    }

    public Set<String> getDependencies() {
        return dependencies;
    }

    public void addDependency(String dependencyName) {
        this.dependencies.add(dependencyName);
    }
    public Set<String> getFields() {
        return fields;
    }

    public void addField(String field) {
        this.fields.add(field);
    }

    public Set<String> getMethods() {
        return methods;
    }

    public void addMethod(String method) {
        this.methods.add(method);
    }
}
