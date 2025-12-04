package com.example.codeatlas.generator;

import com.example.codeatlas.model.ClassNode;
import java.util.Map;

public interface DiagramGenerator {
    String generate(Map<String, ClassNode> classes);
}
