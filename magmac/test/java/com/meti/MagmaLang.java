package com.meti;

public class MagmaLang {

    public static final String LET_KEYWORD = "let ";

    static String renderMagmaDefinition() {
        return renderMagmaDefinition("x");
    }

    static String renderMagmaDefinition(String name) {
        return LET_KEYWORD + name + " = 0;";
    }
}
