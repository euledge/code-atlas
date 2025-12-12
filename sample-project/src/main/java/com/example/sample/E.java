package com.example.dummy;

public class E implements D {
    public String publicField = "This is a public field in E";

    public String getPublicField() {
        return publicField;
    }

    @Override
    public String greet(String name) {
        return "Hello, " + name + " from the sample implementation!";
    }
}