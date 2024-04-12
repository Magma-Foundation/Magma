package com.meti;

import java.util.Collections;
import java.util.List;

public class Lang {
    public static final String CLASS_KEYWORD = "class ";
    public static final String DEF_KEYWORD = "def ";
    public static final String EXPORT_KEYWORD = "export ";
    public static final String PUB_KEYWORD = "pub";
    public static final String PUBLIC_KEYWORD = PUB_KEYWORD + "lic";
    public static final String PUBLIC_KEYWORD_WITH_SPACE = PUBLIC_KEYWORD + " ";
    public static final String BLOCK_START = "{";
    public static final String BLOCK_END = "}";
    public static final String INT_TYPE = "int";
    public static final String VALUE_SEPARATOR = " = ";
    public static final String STATEMENT_END = ";";
    public static final String LET_KEYWORD = "let ";
    public static final String MAGMA_TYPE_SEPARATOR = " : ";
    public static final String I16_TYPE = "I16";
    public static final String FINAL_KEYWORD = "final";
    public static final String FINAL_KEYWORD_WITH_SPACE = FINAL_KEYWORD + " ";
    public static final String CONST_KEYWORD = "const";
    public static final String CONST_KEYWORD_WITH_SPACE = CONST_KEYWORD + " ";
    public static final String PUB_KEYWORD_WITH_SPACE = PUB_KEYWORD + " ";
    public static final String STATIC_KEYWORD = "static";
    public static final String STATIC_KEYWORD_WITH_SPACE = STATIC_KEYWORD + " ";
    public static final String OBJECT_KEYWORD = "object";

    public static String renderDefinitionSuffix(String value) {
        return VALUE_SEPARATOR + value + STATEMENT_END;
    }

    public static String renderMutableMagmaDefinition(String name, String type, String value) {
        return renderMagmaDefinition(LET_KEYWORD, name, type, value);
    }

    public static String renderMagmaDefinition(String mutabilityModifier, String name, String type, String value) {
        return renderMagmaDefinition("", mutabilityModifier, name, type, value);
    }

    public static String renderMagmaDefinition(String flags, String mutabilityModifier, String name, String type, String value) {
        return flags + mutabilityModifier + name + MAGMA_TYPE_SEPARATOR + type + renderDefinitionSuffix(value);
    }

    public static String renderJavaDefinition(String name, String type, String value) {
        return JavaDefinition.render(name, type, value, "");
    }

    public static String renderBlock() {
        return renderBlock("");
    }

    public static String renderBlock(String content) {
        return BLOCK_START + content + BLOCK_END;
    }

    static String renderJavaClass(String name) {
        return renderJavaClass(name, renderBlock());
    }

    static String renderJavaClass(String name, String javaClassBody) {
        return CLASS_KEYWORD + name + javaClassBody;
    }

    static String renderMagmaFunction(String name) {
        return renderMagmaFunction(name, renderBlock());
    }

    static String renderMagmaFunction(String name, String body) {
        return CLASS_KEYWORD + DEF_KEYWORD + name + "() => " + body;
    }

    static String renderObject(String className, String staticContent) {
        return OBJECT_KEYWORD + " " + className + "{" + staticContent + "}";
    }

    static String renderPackage(String name) {
        return renderPackage(Collections.singletonList(name));
    }

     static String renderPackage(List<String> namespace) {
        return "package " + String.join(".", namespace) + ";";
    }
}
