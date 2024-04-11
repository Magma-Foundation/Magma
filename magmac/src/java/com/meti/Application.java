package com.meti;

public class Application {
    public static final String CLASS_KEYWORD = "class ";
    public static final String DEF_KEYWORD = "def ";
    public static final String MAGMA_FUNCTION_BODY_PREFIX = "() => ";
    public static final String MAGMA_FUNCTION_BODY = MAGMA_FUNCTION_BODY_PREFIX + "{}";
    public static final String EXPORT_KEYWORD = "export ";
    public static final String PUBLIC_KEYWORD = "public ";
    public static final String BLOCK_START = "{";
    public static final String BLOCK_END = "}";
    public static final String TEST_DEFINITION_TYPE = "String";
    public static final String TEST_JAVA_DEFINITION_PREFIX = TEST_DEFINITION_TYPE + " ";
    public static final String TEST_DEFINITION_SUFFIX = " = \"Main\";";
    public static final String TEST_MAGMA_DEFINITION_SUFFIX = " : " + TEST_DEFINITION_TYPE + TEST_DEFINITION_SUFFIX;
    public static final String PARAM_START = "(";
    public static final String LET_KEYWORD = "let ";

    public static String renderMagmaDefinition(String name) {
        return LET_KEYWORD + name + " : " + TEST_DEFINITION_TYPE + TEST_DEFINITION_SUFFIX;
    }

    public static String renderJavaDefinition(String name) {
        return TEST_DEFINITION_TYPE + " " + name + TEST_DEFINITION_SUFFIX;
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
        return CLASS_KEYWORD + DEF_KEYWORD + name + MAGMA_FUNCTION_BODY_PREFIX + body;
    }

    static String compileJavaToMagma(String input) throws CompileException {
        var classIndex = input.indexOf(CLASS_KEYWORD);
        if (classIndex == -1) throw createUnknownInputError(input);

        var blockStart = input.indexOf(BLOCK_START);
        var blockEnd = input.lastIndexOf(BLOCK_END);

        var className = input.substring(classIndex + CLASS_KEYWORD.length(), blockStart);
        String content;
        var inputContent = input.substring(blockStart + 1, blockEnd);
        if (inputContent.startsWith(TEST_JAVA_DEFINITION_PREFIX)) {
            var definitionName = inputContent.substring(TEST_JAVA_DEFINITION_PREFIX.length(), inputContent.indexOf(TEST_DEFINITION_SUFFIX));
            content = renderMagmaDefinition(definitionName);
        } else {
            content = "";
        }

        var body = renderBlock(content);
        var rendered = renderMagmaFunction(className, body);
        return input.startsWith(PUBLIC_KEYWORD) ? EXPORT_KEYWORD + rendered : rendered;
    }

    static CompileException createUnknownInputError(String input) {
        return new CompileException("Unknown input: " + input);
    }

    static String compileMagmaToJava(String input) throws CompileException {
        var prefixIndex = input.indexOf(CLASS_KEYWORD + DEF_KEYWORD);
        if (prefixIndex == -1) throw createUnknownInputError(input);

        var className = input.substring(prefixIndex + (CLASS_KEYWORD + DEF_KEYWORD).length(), input.indexOf(PARAM_START));
        var content = input.substring(input.indexOf('{') + 1, input.lastIndexOf('}'));

        String testContent;
        if (content.startsWith(LET_KEYWORD)) {
            var functionName = content.substring(LET_KEYWORD.length(), content.indexOf(TEST_MAGMA_DEFINITION_SUFFIX));
            testContent = renderJavaDefinition(functionName);
        } else {
            testContent = "";
        }

        var rendered = renderJavaClass(className, renderBlock(testContent));
        return input.startsWith(EXPORT_KEYWORD) ? PUBLIC_KEYWORD + rendered : rendered;
    }
}