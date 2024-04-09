package com.meti;

public class JavaLang {
    public static final String CLASS_KEYWORD = "class ";
    public static final String INT_TYPE = "int";
    public static final String LONG_TYPE = "long";

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
        return renderJavaDefinition(name, INT_TYPE);
    }

    static String renderJavaDefinition(String name, String type) {
        return type + " " + name + " = 0;";
    }
}
