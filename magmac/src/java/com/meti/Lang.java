package com.meti;

public class Lang {
    public static final String CLASS_KEYWORD_WITH_SPACE = "class ";
    public static final String PUBLIC_KEYWORD_WITH_SPACE = "public ";
    public static final String EXPORT_KEYWORD_WITH_SPACE = "export ";
    public static final String IMPORT_KEYWORD = "import ";
    public static final String JAVA_IMPORT_SEPARATOR = ".";
    public static final char STATEMENT_END = ';';
    public static final String PACKAGE_KEYWORD_WITH_SPACE = "package ";
    public static final String STATIC_KEYWORD_WITH_SPACE = "static ";
    public static final String JAVA_DEFINITION = "int value = 0;";
    public static final String MAGMA_DEFINITION = "let value : I32 = 0;";
    public static final String BLOCK_START = " {";
    public static final String BLOCK_END = "}";

    public static String renderBlock(String content) {
        return BLOCK_START + content + BLOCK_END;
    }

    public static String renderJavaImport(String parent, String child) {
        return renderJavaImport(parent, child, "");
    }

    public static String renderJavaImport(String parent, String child, String modifierString) {
        return IMPORT_KEYWORD + modifierString + parent + JAVA_IMPORT_SEPARATOR + child + STATEMENT_END;
    }

    public static String renderMagmaImport(String parent, String child) {
        return IMPORT_KEYWORD + "{ " + child + " } from " + parent + STATEMENT_END;
    }

    static String renderMagmaFunction(String name) {
        return renderMagmaFunction(name, "");
    }

    static String renderMagmaFunction(String name, String modifierString) {
        return renderMagmaFunction(name, modifierString, "");
    }

    static String renderMagmaFunction(String name, String modifierString, String content) {
        return modifierString + CLASS_KEYWORD_WITH_SPACE + "def " + name + "() =>" + renderBlock(content);
    }

    static String renderJavaClass(String name) {
        return renderJavaClass(name, "");
    }

    static String renderJavaClass(String name, String modifierString) {
        return renderJavaClass(name, modifierString, "");
    }

    static String renderJavaClass(String name, String modifierString, String content) {
        return modifierString + CLASS_KEYWORD_WITH_SPACE + name + renderBlock(content);
    }
}