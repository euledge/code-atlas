package com.example.sample;

public class E implements D {
    @Override
    public String greet(String name) {
        return "Hello, " + name + " from the sample implementation!";
    }
}