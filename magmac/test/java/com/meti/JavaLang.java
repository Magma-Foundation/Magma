package com.meti;

public class JavaLang {
    public static final String CLASS_KEYWORD = "class ";

    static String renderJavaClass(String name) {
        return renderJavaClass(name, Lang.renderBlock());
    }

    static String renderJavaClass(String name, String value) {
        return CLASS_KEYWORD + name + " " + value;
    }

    static String renderJavaDefinition() {
        return renderJavaDefinition("value");
    }

    static String renderJavaDefinition(String name) {
        return "int " + name + " = 0;";
    }
}
