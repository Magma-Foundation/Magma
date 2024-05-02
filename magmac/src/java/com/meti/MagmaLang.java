package com.meti;

public class MagmaLang {
    public static final String EXPORT_KEYWORD_WITH_SPACE = "export ";
    public static final String I32_KEYWORD = "I32";
    public static final String I64_KEYWORD = "I64";

    public static String renderMagmaDefinition(String name, String type, String value) {
        return "let " + name + " : " + type + Lang.renderDefinitionEnd(value);
    }

    public static String renderMagmaImport(String parent, String child) {
        return Lang.IMPORT_KEYWORD + "{ " + child + " } from " + parent + Lang.STATEMENT_END;
    }

    static String renderMagmaClass(String name) {
        return renderMagmaClass(name, "");
    }

    static String renderMagmaClass(String name, String modifierString) {
        return renderMagmaClass(name, modifierString, "");
    }

    static String renderMagmaClass(String name, String modifierString, String content) {
        return renderMagmaFunction(modifierString + Lang.CLASS_KEYWORD_WITH_SPACE, name, content);
    }

    static String renderMagmaFunction(String name) {
        return renderMagmaFunction(name, "");
    }

    static String renderMagmaFunction(String name, String content) {
        return renderMagmaFunction("", name, content);
    }

    static String renderMagmaFunction(String modifierString, String name, String content) {
        return modifierString + "def " + name + "() =>" + Lang.renderBlock(content);
    }
}
