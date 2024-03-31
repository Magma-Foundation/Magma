package com.meti;

public class JavaLang {
    static String renderJavaClass(String name, String content) {
        return Compiler.renderJavaClass("", name, content);
    }

    static String renderRecord(String name) {
        return renderRecord("", name);
    }

    static String renderRecord(String prefix, String name) {
        return renderRecord(prefix, name, "{}");
    }

    static String renderRecord(String prefix, String name, String body) {
        return prefix + "record " + name + "()" + body;
    }
}