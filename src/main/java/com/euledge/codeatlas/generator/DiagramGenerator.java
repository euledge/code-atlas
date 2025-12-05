package com.euledge.codeatlas.generator;

import com.euledge.codeatlas.model.ClassNode;
import java.util.Map;

public interface DiagramGenerator {
    String generate(Map<String, ClassNode> classes, boolean showDetails);
}
