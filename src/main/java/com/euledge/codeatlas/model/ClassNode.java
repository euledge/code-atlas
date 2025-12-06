package com.euledge.codeatlas.model;

import java.util.HashSet;
import java.util.Set;

/**
 * A data model representing a single class and its structural information.
 * This includes its name, inheritance, interfaces, dependencies, fields, and methods.
 */
public class ClassNode {
    private String name;
    private String superClassName;
    private Set<String> interfaces = new HashSet<>();
    private Set<String> dependencies = new HashSet<>();
    private Set<String> fields = new HashSet<>();
    private Set<String> methods = new HashSet<>();

    /**
     * Constructs a new ClassNode with the specified name.
     *
     * @param name The fully qualified name of the class.
     */
    public ClassNode(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the class.
     *
     * @return The fully qualified class name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the name of the superclass.
     *
     * @return The fully qualified name of the superclass, or null if it has no superclass (other than Object).
     */
    public String getSuperClassName() {
        return superClassName;
    }

    /**
     * Sets the name of the superclass.
     *
     * @param superClassName The fully qualified name of the superclass.
     */
    public void setSuperClassName(String superClassName) {
        this.superClassName = superClassName;
    }

    /**
     * Gets the set of implemented interfaces.
     *
     * @return A set of fully qualified interface names.
     */
    public Set<String> getInterfaces() {
        return interfaces;
    }

    /**
     * Adds an interface to the set of implemented interfaces.
     *
     * @param interfaceName The fully qualified name of the interface to add.
     */
    public void addInterface(String interfaceName) {
        this.interfaces.add(interfaceName);
    }

    /**
     * Gets the set of class dependencies.
     *
     * @return A set of fully qualified names of dependent classes.
     */
    public Set<String> getDependencies() {
        return dependencies;
    }

    /**
     * Adds a dependency to the set of dependencies.
     *
     * @param dependencyName The fully qualified name of the dependent class to add.
     */
    public void addDependency(String dependencyName) {
        this.dependencies.add(dependencyName);
    }
    /**
     * Gets the set of fields in this class.
     *
     * @return A set of strings representing the fields.
     */
    public Set<String> getFields() {
        return fields;
    }

    /**
     * Adds a field to the set of fields.
     *
     * @param field A string representation of the field to add.
     */
    public void addField(String field) {
        this.fields.add(field);
    }

    /**
     * Gets the set of methods in this class.
     *
     * @return A set of strings representing the methods.
     */
    public Set<String> getMethods() {
        return methods;
    }

    /**
     * Adds a method to the set of methods.
     *
     * @param method A string representation of the method to add.
     */
    public void addMethod(String method) {
        this.methods.add(method);
    }
}
