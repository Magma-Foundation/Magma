package com.meti;

public class JavaLang {
    static String renderJavaClass(String name, String content) {
        return Compiler.renderJavaClass("", name, content);
    }

    static String renderDefinition(String name, String type) {
        return renderDefinition(name, type, "0");
    }

    static String renderDefinition(String name, String type, String value) {
        return type + " " + name + " = " + value + ";";
    }
}