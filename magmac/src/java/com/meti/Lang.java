package com.meti;

public class Lang {
    public static final String CLASS_KEYWORD = "class ";
    public static final String DEF_KEYWORD = "def ";
    public static final String EXPORT_KEYWORD = "export ";
    public static final String PUBLIC_KEYWORD = "public ";
    public static final String BLOCK_START = "{";
    public static final String BLOCK_END = "}";
    public static final String INT_TYPE = "int";
    public static final String VALUE_SEPARATOR = " = ";
    public static final String STATEMENT_END = ";";
    public static final String LET_KEYWORD = "let ";
    public static final String MAGMA_TYPE_SEPARATOR = " : ";
    public static final String I16_TYPE = "I16";
    public static final String FINAL_KEYWORD = "final ";
    public static final String CONST_KEYWORD = "const ";

    public static String renderDefinitionSuffix(String value) {
        return VALUE_SEPARATOR + value + STATEMENT_END;
    }

    public static String renderMutableMagmaDefinition(String name, String type, String value) {
        return renderMagmaDefinition(LET_KEYWORD, name, type, value);
    }

    public static String renderMagmaDefinition(String mutabilityModifier, String name, String type, String value) {
        return mutabilityModifier + name + MAGMA_TYPE_SEPARATOR + type + renderDefinitionSuffix(value);
    }

    public static String renderJavaDefinition(String name, String type, String value) {
        return renderJavaDefinition("", name, type, value);
    }

    public static String renderJavaDefinition(String finalString, String name, String type, String value) {
        return finalString + type + " " + name + renderDefinitionSuffix(value);
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
}
