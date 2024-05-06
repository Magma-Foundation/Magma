package com.meti;

import java.util.Collections;
import java.util.stream.Collectors;

import static com.meti.Lang.*;

public class MagmaLang {
    public static final String EXPORT_KEYWORD_WITH_SPACE = "export ";
    public static final String I32_KEYWORD = "I32";
    public static final String I64_KEYWORD = "I64";
    public static final String LET_KEYWORD_WITH_SPACE = "let ";
    public static final String CONST_KEYWORD_WITH_SPACE = "const ";

    public static String renderMagmaDefinition(Node magmaDefinition) {
        return magmaDefinition.apply("mutability-modifier").flatMap(Attribute::asString).orElseThrow() +
               renderMagmaDeclaration(magmaDefinition.apply("name").flatMap(Attribute::asString).orElseThrow(), magmaDefinition.apply("type").flatMap(Attribute::asString).orElseThrow()) +
               renderDefinitionEnd(magmaDefinition.apply("value").flatMap(Attribute::asString).orElseThrow());
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
        return renderMagmaFunction(MapNodeBuilder.NodeBuilder
                .string("modifier-string", modifierString + CLASS_KEYWORD_WITH_SPACE)
                .string("name", name)
                .string("content", content)
                .build(""));
    }

    static String renderMagmaFunction(Node node) {
        var modifierString1 = node.apply("modifier-string").flatMap(Attribute::asString).orElse("");
        var name1 = node.apply("name").flatMap(Attribute::asString).orElse("");
        var paramString1 = node.apply("param-string").flatMap(Attribute::asString).orElse("");
        var content1 = node.apply("content").flatMap(Attribute::asString).orElse("");
        var indent = node.apply("indent").flatMap(Attribute::asInteger).orElse(0);

        var annotations = node.apply("annotations")
                .flatMap(Attribute::asListOfStrings)
                .orElse(Collections.emptyList());

        var annotationsString = annotations.stream()
                .map(annotation -> "\t".repeat(indent) + "@"+ annotation + "\n")
                .collect(Collectors.joining());

        return annotationsString + "\t".repeat(indent) + modifierString1 + "def " + name1 +
               PARAM_START + paramString1 +
               PARAM_END + " =>" + renderBlock(MapNodeBuilder.NodeBuilder
                       .string("content", content1)
                       .integer("indent", indent)
                       .build("")) + "\n";
    }
}
