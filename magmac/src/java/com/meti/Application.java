package com.meti;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class Application {
    static String compileJavaToMagma(String input) throws CompileException {
        var classIndex = input.indexOf(Lang.CLASS_KEYWORD);
        if (classIndex == -1) throw createUnknownInputError(input);

        var blockStart = input.indexOf(Lang.BLOCK_START);
        var blockEnd = input.lastIndexOf(Lang.BLOCK_END);

        var className = input.substring(classIndex + Lang.CLASS_KEYWORD.length(), blockStart);
        var inputContent = input.substring(blockStart + 1, blockEnd);
        var content = Arrays.stream(inputContent.split(";"))
                .map(String::strip)
                .filter(value -> !value.isEmpty())
                .map(Application::compileJavaDefinition)
                .flatMap(Optional::stream)
                .collect(Collectors.joining());

        var body = Lang.renderBlock(content);
        var rendered = Lang.renderMagmaFunction(className, body);
        return input.startsWith(Lang.PUBLIC_KEYWORD) ? Lang.EXPORT_KEYWORD + rendered : rendered;
    }

    private static Optional<String> compileJavaDefinition(String inputContent) {
        var typeNameEnd = inputContent.indexOf(Lang.VALUE_SEPARATOR);
        if (typeNameEnd == -1) return Optional.empty();

        var typeName = inputContent.substring(0, typeNameEnd);
        var separator = typeName.indexOf(' ');
        if (separator == -1) return Optional.empty();

        var inputType = typeName.substring(0, separator);
        var outputType = inputType.equals(Lang.INT_TYPE) ? Lang.I16_TYPE : inputType;

        var name = typeName.substring(separator + 1);
        var value = inputContent.substring(typeNameEnd + Lang.VALUE_SEPARATOR.length());

        return Optional.of(Lang.renderMutableMagmaDefinition(name, outputType, value));
    }

    static CompileException createUnknownInputError(String input) {
        return new CompileException("Unknown input: " + input);
    }

    static String compileMagmaToJava(String input) throws CompileException {
        var prefixIndex = input.indexOf(Lang.CLASS_KEYWORD + Lang.DEF_KEYWORD);
        if (prefixIndex == -1) throw createUnknownInputError(input);

        var className = input.substring(prefixIndex + (Lang.CLASS_KEYWORD + Lang.DEF_KEYWORD).length(), input.indexOf("("));
        var content = Arrays.stream(input.substring(input.indexOf('{') + 1, input.lastIndexOf('}')).split(";"))
                .map(String::strip)
                .filter(value -> !value.isEmpty())
                .map(Application::compileMagmaDefinition)
                .flatMap(Optional::stream)
                .collect(Collectors.joining());

        var rendered = Lang.renderJavaClass(className, Lang.renderBlock(content));
        return input.startsWith(Lang.EXPORT_KEYWORD) ? Lang.PUBLIC_KEYWORD + rendered : rendered;
    }

    private static Optional<String> compileMagmaDefinition(String content) {
        if (!content.startsWith(Lang.LET_KEYWORD)) return Optional.empty();

        var typeSeparator = content.indexOf(Lang.MAGMA_TYPE_SEPARATOR);
        var functionName = content.substring(Lang.LET_KEYWORD.length(), typeSeparator);

        var valueSeparator = content.indexOf(Lang.VALUE_SEPARATOR);
        var inputType = content.substring(typeSeparator + Lang.MAGMA_TYPE_SEPARATOR.length(), valueSeparator);
        var outputType = inputType.equals(Lang.I16_TYPE) ? Lang.INT_TYPE : inputType;

        var value = content.substring(valueSeparator + Lang.VALUE_SEPARATOR.length());
        return Optional.of(Lang.renderJavaDefinition(functionName, outputType, value));
    }
}