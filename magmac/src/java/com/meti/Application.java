package com.meti;

import java.util.*;
import java.util.stream.Collectors;

import static com.meti.Lang.*;

public class Application {

    static String compileJavaToMagma(String input) throws CompileException {
        var classIndex = input.indexOf(CLASS_KEYWORD);
        if (classIndex == -1) throw createUnknownInputError(input);

        var blockStart = input.indexOf(BLOCK_START);
        var blockEnd = input.lastIndexOf(BLOCK_END);

        var className = input.substring(classIndex + CLASS_KEYWORD.length(), blockStart);
        var inputContent = input.substring(blockStart + 1, blockEnd);
        var content = Arrays.stream(inputContent.split(";")).map(String::strip).filter(value -> !value.isEmpty()).map(Application::compileJavaDefinition).flatMap(Optional::stream).collect(Collectors.joining());

        var body = renderBlock(content);
        var rendered = renderMagmaFunction(className, body);
        return input.startsWith(PUBLIC_KEYWORD_WITH_SPACE) ? EXPORT_KEYWORD + rendered : rendered;
    }

    private static Optional<String> compileJavaDefinition(String inputContent) {
        var beforeEnd = inputContent.indexOf(VALUE_SEPARATOR);
        if (beforeEnd == -1) return Optional.empty();

        var before = inputContent.substring(0, beforeEnd);
        var nameSeparator = before.lastIndexOf(' ');
        if (nameSeparator == -1) return Optional.empty();

        var beforeName = before.substring(0, nameSeparator);
        String inputType;
        Set<String> flags;

        var typeSeparator = beforeName.lastIndexOf(' ');
        if (typeSeparator == -1) {
            inputType = beforeName;
            flags = Collections.emptySet();
        } else {
            inputType = beforeName.substring(typeSeparator + 1);
            flags = Arrays.stream(beforeName.substring(0, typeSeparator).split(" ")).map(String::strip).filter(value -> !value.isEmpty()).collect(Collectors.toSet());
        }

        var outputType = inputType.equals(INT_TYPE) ? I16_TYPE : inputType;

        var name = before.substring(nameSeparator + 1);
        var value = inputContent.substring(beforeEnd + VALUE_SEPARATOR.length());
        var mutabilityModifier = flags.contains(FINAL_KEYWORD) ? CONST_KEYWORD_WITH_SPACE : LET_KEYWORD;
        var flagString = flags.contains(PUBLIC_KEYWORD) ? PUB_KEYWORD_WITH_SPACE : "";

        return Optional.of(renderMagmaDefinition(flagString, mutabilityModifier, name, outputType, value));
    }

    static CompileException createUnknownInputError(String input) {
        return new CompileException("Unknown input: " + input);
    }

    static String compileMagmaToJava(String input) throws CompileException {
        var prefixIndex = input.indexOf(CLASS_KEYWORD + DEF_KEYWORD);
        if (prefixIndex == -1) throw createUnknownInputError(input);

        var className = input.substring(prefixIndex + (CLASS_KEYWORD + DEF_KEYWORD).length(), input.indexOf("("));
        var content = Arrays.stream(input.substring(input.indexOf('{') + 1, input.lastIndexOf('}')).split(";")).map(String::strip).filter(value -> !value.isEmpty()).map(Application::compileMagmaDefinition).flatMap(Optional::stream).collect(Collectors.joining());

        var rendered = renderJavaClass(className, renderBlock(content));
        return input.startsWith(EXPORT_KEYWORD) ? PUBLIC_KEYWORD_WITH_SPACE + rendered : rendered;
    }

    private static Optional<String> compileMagmaDefinition(String content) {
        var letIndex = content.indexOf(LET_KEYWORD);
        var constIndex = content.indexOf(CONST_KEYWORD_WITH_SPACE);
        if (letIndex != -1 || constIndex != -1) {
            var isMutable = letIndex != -1;

            var typeSeparator = content.indexOf(MAGMA_TYPE_SEPARATOR);
            var nameStart = isMutable ? (LET_KEYWORD.length() + letIndex) : (CONST_KEYWORD_WITH_SPACE.length() + constIndex);
            var name = content.substring(nameStart, typeSeparator);

            var valueSeparator = content.indexOf(VALUE_SEPARATOR);
            var inputType = content.substring(typeSeparator + MAGMA_TYPE_SEPARATOR.length(), valueSeparator);
            var outputType = inputType.equals(I16_TYPE) ? INT_TYPE : inputType;

            var value = content.substring(valueSeparator + VALUE_SEPARATOR.length());
            var flags = Arrays.stream(content.substring(0, nameStart).strip().split(" "))
                    .map(String::strip)
                    .filter(arg -> !arg.isEmpty())
                    .collect(Collectors.toSet());

            var outputFlags = new ArrayList<String>();
            if(flags.contains(PUB_KEYWORD)) outputFlags.add(PUBLIC_KEYWORD);
            if(flags.contains(CONST_KEYWORD)) outputFlags.add(FINAL_KEYWORD);

            return Optional.of(renderJavaDefinition(name, outputType, value, outputFlags));
        } else {
            return Optional.empty();
        }

    }
}