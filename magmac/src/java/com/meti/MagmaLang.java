package com.meti;

import static com.meti.Lang.*;

public class MagmaLang {
    public static final String EXPORT_KEYWORD_WITH_SPACE = "export ";
    public static final String I32_KEYWORD = "I32";
    public static final String I64_KEYWORD = "I64";
    public static final String LET_KEYWORD_WITH_SPACE = "let ";
    public static final String CONST_KEYWORD_WITH_SPACE = "const ";

    public static String renderMagmaDefinition(Node magmaDefinition) {
        return magmaDefinition.apply("mutability-modifier").orElseThrow() +
               renderMagmaDeclaration(magmaDefinition.apply("name").orElseThrow(), magmaDefinition.apply("type").orElseThrow()) +
               renderDefinitionEnd(magmaDefinition.apply("value").orElseThrow());
    }

    public static String renderMagmaDeclaration(String name, String type) {
        return name + " : " + type;
    }

    public static String renderMagmaImport(String parent, String child) {
        return IMPORT_KEYWORD + "{ " + child + " } from " + parent + STATEMENT_END;
    }

    static String renderMagmaClass(String name) {
        return renderMagmaClass(name, "");
    }

    static String renderMagmaClass(String name, String modifierString) {
        return renderMagmaClass(name, modifierString, "");
    }

    static String renderMagmaClass(String name, String modifierString, String content) {
        return renderMagmaFunction(modifierString + CLASS_KEYWORD_WITH_SPACE, name, content);
    }

    static String renderMagmaFunction(String name) {
        return renderMagmaFunction(name, "");
    }

    static String renderMagmaFunction(String name, String content) {
        return renderMagmaFunction("", name, content);
    }

    static String renderMagmaFunction(String modifierString, String name, String content) {
        return renderMagmaFunction(new MapNodeBuilder()
                .with("modifier-string", modifierString)
                .with("name", name)
                .with("param-string", "")
                .with("content", content)
                .complete());
    }

    static String renderMagmaFunction(Node node) {
        var modifierString1 = node.apply("modifier-string").orElse("");
        var name1 = node.apply("name").orElse("");
        var paramString1 = node.apply("param-string").orElse("");
        var content1 = node.apply("content").orElse("");

        return modifierString1 + "def " + name1 + PARAM_START + paramString1 + PARAM_END + " =>" + renderBlock(content1);
    }
}
