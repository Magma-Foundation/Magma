package com.meti;

public class MagmaLang {

    public static final String LET_KEYWORD = "let ";
    public static final String I64 = "I64";

    static String renderMagmaDefinition() {
        return renderMagmaDefinition("x");
    }

    static String renderMagmaDefinition(String name) {
        return renderMagmaDefinitionWithTypeString(name, "");
    }

    static String renderMagmaDefinition(String name, String type) {
        return renderMagmaDefinitionWithTypeString(name, " : " + type);
    }

    static String renderMagmaDefinitionWithTypeString(String name, String typeString) {
        return LET_KEYWORD + name + typeString + " = 0;";
    }
}
