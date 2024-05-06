package com.meti;

import java.util.Collections;
import java.util.stream.Collectors;

import static com.meti.MapNodeBuilder.NodeBuilder;

public class JavaLang {
    public static final String JAVA_IMPORT_SEPARATOR = ".";
    public static final String PACKAGE_KEYWORD_WITH_SPACE = "package ";
    public static final String STATIC_KEYWORD_WITH_SPACE = "static ";
    public static final String INT_KEYWORD = "int";
    public static final String LONG_KEYWORD = "long";
    public static final String VOID_TYPE_WITH_SPACE = "void ";
    public static final String FINAL_KEYWORD = "final";

    public static String renderMethodContent(String methodContent) {
        return Lang.PARAM_START + methodContent + Lang.PARAM_END + Lang.renderBlock(NodeBuilder
                .string("content", "")
                .build());
    }

    public static String renderJavaDefinition(Node javaDefinitionNode) {
        return javaDefinitionNode.apply("modifier-string").flatMap(Attribute::asString).orElse("") +
               renderJavaDeclaration(javaDefinitionNode.apply("name").flatMap(Attribute::asString).orElseThrow(),
                       javaDefinitionNode.apply("type").flatMap(Attribute::asString).orElseThrow()) +
               Lang.renderDefinitionEnd(javaDefinitionNode.apply("value").flatMap(Attribute::asString).orElseThrow());
    }

    public static String renderJavaDeclaration(String name, String type) {
        return type + Lang.TYPE_NAME_SEPARATOR + name;
    }

    public static String renderJavaImport(String parent, String child) {
        return renderJavaImport(parent, child, "");
    }

    public static String renderJavaImport(String parent, String child, String modifierString) {
        return Lang.IMPORT_KEYWORD + modifierString + parent + JAVA_IMPORT_SEPARATOR + child + Lang.STATEMENT_END;
    }

    static String renderJavaClass(String name) {
        return renderJavaClass(name, "");
    }

    static String renderJavaClass(String name, String modifierString) {
        return renderJavaClass(name, modifierString, "");
    }

    static String renderJavaClass(String name, String modifierString, String content) {
        return modifierString + Lang.CLASS_KEYWORD_WITH_SPACE + name + Lang.renderBlock(NodeBuilder
                .string("content", content)
                .build());
    }

    static String renderJavaMethod(String name) {
        return renderJavaMethod(name, "");
    }

    static String renderJavaMethod(String name, String paramString) {
        return renderJavaMethod(NodeBuilder.string("annotation-string", "")
                .string("name", name)
                .string("param-string", paramString)
                .build());
    }

    static String renderJavaMethod(Node node) {
        var s = node.apply("annotations")
                .flatMap(Attribute::asListOfStrings)
                .orElse(Collections.emptyList())
                .stream()
                .map(annotation -> "@" + annotation + "\n")
                .collect(Collectors.joining());

        var s1 = node.apply("name").flatMap(Attribute::asString).orElse("");
        var methodContent = node.apply("param-string").flatMap(Attribute::asString).orElse("");
        return s + VOID_TYPE_WITH_SPACE + s1 + renderMethodContent(methodContent);
    }
}
