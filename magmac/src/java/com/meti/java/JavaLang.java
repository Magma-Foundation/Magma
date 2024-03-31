package com.meti.java;

public class JavaLang {
    public static String renderJavaClass(String name, String content) {
        return new JavaClassNodeBuilder().setPrefix("").setName(name).setContent(content).createJavaClassNode().renderJavaClass();
    }

    public static String renderRecord(String name) {
        return renderRecord("", name);
    }

    public static String renderRecord(String prefix, String name) {
        return renderRecord(prefix, name, "{}");
    }

    public static String renderRecord(String prefix, String name, String body) {
        return prefix + "record " + name + "()" + body;
    }
}