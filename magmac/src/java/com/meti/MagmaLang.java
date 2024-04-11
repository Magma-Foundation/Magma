package com.meti;

public class MagmaLang {

    public static final String LET_KEYWORD = "let ";
    public static final String I64 = "I64";

    static String renderMagmaDefinitionWithTypeString(String name, String typeString, String value) {
        return LET_KEYWORD + name + typeString + " = " + value + ";";
    }
}
