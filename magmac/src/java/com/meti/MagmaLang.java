package com.meti;

public class MagmaLang {
    public static final String EXPORT_KEYWORD_WITH_SPACE = "export ";
    public static final String I32_KEYWORD = "I32";
    public static final String I64_KEYWORD = "I64";

    public static String renderMagmaDefinition(String name, String type) {
        return "let " + name + " : " + type + " = 0;";
    }

    public static String renderMagmaImport(String parent, String child) {
        return Lang.IMPORT_KEYWORD + "{ " + child + " } from " + parent + Lang.STATEMENT_END;
    }

    static String renderMagmaFunction(String name) {
        return renderMagmaFunction(name, "");
    }

    static String renderMagmaFunction(String name, String modifierString) {
        return renderMagmaFunction(name, modifierString, "");
    }

    static String renderMagmaFunction(String name, String modifierString, String content) {
        return modifierString + Lang.CLASS_KEYWORD_WITH_SPACE + "def " + name + "() =>" + Lang.renderBlock(content);
    }
}
