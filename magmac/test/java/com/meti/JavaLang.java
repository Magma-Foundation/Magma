package com.meti;

public class JavaLang {
    static String renderJavaClass(String name) {
        return renderJavaClass(name, Lang.renderBlock());
    }

    static String renderJavaClass(String name, String value) {
        return ApplicationTest.CLASS_KEYWORD + name + " " + value;
    }

    static String renderJavaDefinition() {
        return "int value = 0;";
    }
}
