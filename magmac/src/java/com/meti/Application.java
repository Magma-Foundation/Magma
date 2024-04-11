package com.meti;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class Application {
    public static final String CLASS_KEYWORD = "class ";
    public static final String DEF_KEYWORD = "def ";
    public static final String EXPORT_KEYWORD = "export ";
    public static final String PUBLIC_KEYWORD = "public ";
    public static final String BLOCK_START = "{";
    public static final String BLOCK_END = "}";
    public static final String INT_TYPE = "int";
    public static final String VALUE_SEPARATOR = " = ";
    public static final String STATEMENT_END = ";";
    public static final String LET_KEYWORD = "let ";
    public static final String MAGMA_TYPE_SEPARATOR = " : ";
    public static final String I16_TYPE = "I16";
    public static final String FINAL_KEYWORD = "final ";
    public static final String CONST_KEYWORD = "const ";

    public static String renderDefinitionSuffix(String value) {
        return VALUE_SEPARATOR + value + STATEMENT_END;
    }

    public static String renderMutableMagmaDefinition(String name, String type, String value) {
        return renderMagmaDefinition(LET_KEYWORD, name, type, value);
    }

    public static String renderMagmaDefinition(String mutabilityModifier, String name, String type, String value) {
        return mutabilityModifier + name + MAGMA_TYPE_SEPARATOR + type + renderDefinitionSuffix(value);
    }

    public static String renderJavaDefinition(String name, String type, String value) {
        return renderJavaDefinition("", name, type, value);
    }

    public static String renderJavaDefinition(String finalString, String name, String type, String value) {
        return finalString + type + " " + name + renderDefinitionSuffix(value);
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
        return CLASS_KEYWORD + DEF_KEYWORD + name + "() => " + body;
    }

    static String compileJavaToMagma(String input) throws CompileException {
        var classIndex = input.indexOf(CLASS_KEYWORD);
        if (classIndex == -1) throw createUnknownInputError(input);

        var blockStart = input.indexOf(BLOCK_START);
        var blockEnd = input.lastIndexOf(BLOCK_END);

        var className = input.substring(classIndex + CLASS_KEYWORD.length(), blockStart);
        var inputContent = input.substring(blockStart + 1, blockEnd);
        var content = Arrays.stream(inputContent.split(";"))
                .map(String::strip)
                .filter(value -> !value.isEmpty())
                .map(Application::compileJavaDefinition)
                .flatMap(Optional::stream)
                .collect(Collectors.joining());

        var body = renderBlock(content);
        var rendered = renderMagmaFunction(className, body);
        return input.startsWith(PUBLIC_KEYWORD) ? EXPORT_KEYWORD + rendered : rendered;
    }

    private static Optional<String> compileJavaDefinition(String inputContent) {
        var typeNameEnd = inputContent.indexOf(VALUE_SEPARATOR);
        if (typeNameEnd == -1) return Optional.empty();

        var typeName = inputContent.substring(0, typeNameEnd);
        var separator = typeName.indexOf(' ');
        if (separator == -1) return Optional.empty();

        var inputType = typeName.substring(0, separator);
        var outputType = inputType.equals(INT_TYPE) ? I16_TYPE : inputType;

        var name = typeName.substring(separator + 1);
        var value = inputContent.substring(typeNameEnd + VALUE_SEPARATOR.length());

        return Optional.of(renderMutableMagmaDefinition(name, outputType, value));
    }

    static CompileException createUnknownInputError(String input) {
        return new CompileException("Unknown input: " + input);
    }

    static String compileMagmaToJava(String input) throws CompileException {
        var prefixIndex = input.indexOf(CLASS_KEYWORD + DEF_KEYWORD);
        if (prefixIndex == -1) throw createUnknownInputError(input);

        var className = input.substring(prefixIndex + (CLASS_KEYWORD + DEF_KEYWORD).length(), input.indexOf("("));
        var content = Arrays.stream(input.substring(input.indexOf('{') + 1, input.lastIndexOf('}')).split(";"))
                .map(String::strip)
                .filter(value -> !value.isEmpty())
                .map(Application::compileMagmaDefinition)
                .flatMap(Optional::stream)
                .collect(Collectors.joining());

        var rendered = renderJavaClass(className, renderBlock(content));
        return input.startsWith(EXPORT_KEYWORD) ? PUBLIC_KEYWORD + rendered : rendered;
    }

    private static Optional<String> compileMagmaDefinition(String content) {
        if (!content.startsWith(LET_KEYWORD)) return Optional.empty();

        var typeSeparator = content.indexOf(MAGMA_TYPE_SEPARATOR);
        var functionName = content.substring(LET_KEYWORD.length(), typeSeparator);

        var valueSeparator = content.indexOf(VALUE_SEPARATOR);
        var inputType = content.substring(typeSeparator + MAGMA_TYPE_SEPARATOR.length(), valueSeparator);
        var outputType = inputType.equals(I16_TYPE) ? INT_TYPE : inputType;

        var value = content.substring(valueSeparator + VALUE_SEPARATOR.length());
        return Optional.of(renderJavaDefinition(functionName, outputType, value));
    }
}