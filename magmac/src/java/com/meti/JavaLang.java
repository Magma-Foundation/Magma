package com.meti;

public class JavaLang {
    public static final String JAVA_IMPORT_SEPARATOR = ".";
    public static final String PACKAGE_KEYWORD_WITH_SPACE = "package ";
    public static final String STATIC_KEYWORD_WITH_SPACE = "static ";
    public static final String INT_KEYWORD = "int";
    public static final String LONG_KEYWORD = "long";
    public static final String VOID_TYPE_WITH_SPACE = "void ";
    public static final String FINAL_KEYWORD = "final";

    public static String renderMethodContent(String methodContent) {
        return Lang.PARAM_START + methodContent + Lang.PARAM_END + Lang.renderBlock("");
    }

    public static String renderJavaDefinition(JavaDefinitionNode javaDefinitionNode) {
        return javaDefinitionNode.find("modifier-string").orElseThrow() + renderJavaDeclaration(javaDefinitionNode.find("name").orElseThrow(), javaDefinitionNode.find("type").orElseThrow()) + Lang.renderDefinitionEnd(javaDefinitionNode.find("value").orElseThrow());
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
        return modifierString + Lang.CLASS_KEYWORD_WITH_SPACE + name + Lang.renderBlock(content);
    }

    static String renderJavaMethod(String name) {
        return renderJavaMethod(name, "");
    }

    static String renderJavaMethod(String name, String paramString) {
        return VOID_TYPE_WITH_SPACE + name + renderMethodContent(paramString);
    }
}
