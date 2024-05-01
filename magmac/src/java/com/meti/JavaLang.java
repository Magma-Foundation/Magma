package com.meti;

public class JavaLang {
    public static final String JAVA_IMPORT_SEPARATOR = ".";
    public static final String PACKAGE_KEYWORD_WITH_SPACE = "package ";
    public static final String STATIC_KEYWORD_WITH_SPACE = "static ";
    public static final String INT_KEYWORD = "int";
    public static final String LONG_KEYWORD = "long";

    public static String renderJavaDefinition(String name, String type, String value) {
        return type + Lang.TYPE_NAME_SEPARATOR + name + Lang.renderDefinitionEnd(value);
    }

    public static String renderJavaImport(String parent, String child) {
        return renderJavaImport(parent, child, "");
    }

    public static String renderJavaImport(String parent, String child, String modifierString) {
        return Lang.IMPORT_KEYWORD + modifierString + parent + JAVA_IMPORT_SEPARATOR + child + Lang.STATEMENT_END;
    }

    static String renderJavaClass(String name) {
        return renderJavaClass(name, "");
    }

    static String renderJavaClass(String name, String modifierString) {
        return renderJavaClass(name, modifierString, "");
    }

    static String renderJavaClass(String name, String modifierString, String content) {
        return modifierString + Lang.CLASS_KEYWORD_WITH_SPACE + name + Lang.renderBlock(content);
    }
}
