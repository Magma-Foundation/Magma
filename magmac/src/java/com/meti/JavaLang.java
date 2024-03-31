package com.meti;

public class JavaLang {
    static String renderJavaClass(String name, String content) {
        return Compiler.renderJavaClass("", name, content);
    }
}