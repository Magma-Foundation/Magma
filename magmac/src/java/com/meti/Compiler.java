package com.meti;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.meti.JavaLang.*;
import static com.meti.Lang.*;
import static com.meti.MagmaLang.*;

public class Compiler {
    public static final String FINAL_KEYWORD_WITH_SPACE = FINAL_KEYWORD + " ";

    static String run(String input) {
        var statements = split(input);
        var outputBuilder = new StringBuilder();
        for (String statement : statements) {
            outputBuilder.append(compileStatement(statement));
        }
        return outputBuilder.toString();
    }

    private static ArrayList<String> split(String input) {
        var statements = new ArrayList<String>();
        var inputBuilder = new StringBuilder();
        var depth = 0;

        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (c == STATEMENT_END && depth == 0) {
                statements.add(inputBuilder.toString());
                inputBuilder = new StringBuilder();
            } else if (c == '}' && depth == 1) {
                inputBuilder.append(c);
                depth = 0;

                statements.add(inputBuilder.toString());
                inputBuilder = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
                inputBuilder.append(c);
            }
        }
        statements.add(inputBuilder.toString());
        return statements;
    }

    private static String compileStatement(String input) {
        return compilePackage(input)
                .or(() -> compileImports(input))
                .or(() -> compileClass(input))
                .orElse("");
    }

    private static Optional<String> compilePackage(String input) {
        return input.startsWith(PACKAGE_KEYWORD_WITH_SPACE) ? Optional.of("") : Optional.empty();
    }

    private static Optional<String> compileImports(String input) {
        if (!input.startsWith(IMPORT_KEYWORD)) return Optional.empty();

        var truncated = input.substring(IMPORT_KEYWORD.length());
        var segments = truncated.startsWith(STATIC_KEYWORD_WITH_SPACE)
                ? truncated.substring(STATIC_KEYWORD_WITH_SPACE.length())
                : truncated;

        var separator = segments.indexOf(JAVA_IMPORT_SEPARATOR);
        var parent = segments.substring(0, separator);
        var child = segments.substring(separator + 1);
        return Optional.of(renderMagmaImport(parent, child));
    }

    private static Optional<String> compileClass(String input) {
        var nameStart = input.indexOf(CLASS_KEYWORD_WITH_SPACE) + CLASS_KEYWORD_WITH_SPACE.length();
        var contentStart = input.indexOf(BLOCK_START);
        var contentEnd = input.lastIndexOf(BLOCK_END);
        if (contentEnd == -1) return Optional.empty();

        var inputContent = input.substring(contentStart + BLOCK_START.length(), contentEnd);
        var outputContent = compileClassContent(inputContent);

        var name = input.substring(nameStart, contentStart);
        var modifierString = input.startsWith(PUBLIC_KEYWORD_WITH_SPACE) ? EXPORT_KEYWORD_WITH_SPACE : "";
        return Optional.of(renderMagmaClass(name, modifierString, outputContent));
    }

    private static String compileClassContent(String input) {
        var lines = split(input);
        var builder = new StringBuilder();
        for (String line : lines) {
            builder.append(compileClassMember(line));
        }

        return builder.toString();
    }

    private static String compileClassMember(String input) {
        return compileMethod(input)
                .or(() -> compileDefinition(input))
                .orElse("");
    }

    private static Optional<String> compileMethod(String input) {
        var annotationEnd = input.indexOf(VOID_TYPE_WITH_SPACE);
        if (annotationEnd == -1) return Optional.empty();

        var annotations = Arrays.stream(input.substring(0, annotationEnd)
                .split("\n"))
                .map(String::strip)
                .filter(value -> !value.isEmpty())
                .collect(Collectors.toList());

        var paramStart = input.indexOf(PARAM_START);
        var paramEnd = input.indexOf(PARAM_END, paramStart);
        var inputParamContent = input.substring(paramStart + 1, paramEnd);
        var inputParams = inputParamContent.split(",");

        var outputParams = new ArrayList<String>();
        for (var inputParam : inputParams) {
            var stripped = inputParam.strip();
            if (stripped.isEmpty()) continue;

            var compiledParam = compileDefinition(stripped);
            if (compiledParam.isEmpty()) {
                return Optional.empty();
            }

            outputParams.add(compiledParam.get());
        }

        var outputParamContent = String.join(", ", outputParams);

        var name = input.substring(VOID_TYPE_WITH_SPACE.length() + annotationEnd, paramStart);
        return Optional.of(renderMagmaFunction(MapNodeBuilder.Empty
                .stringList("annotations", annotations)
                .string("name", name)
                .string("param-string", outputParamContent)
                .integer("indent", 1)
                .build()));
    }

    private static Optional<String> compileDefinition(String input) {
        var valueSeparator = input.indexOf(VALUE_SEPARATOR);
        var keysString = input.substring(0, valueSeparator == -1 ? input.length() : valueSeparator);

        var separator = keysString.lastIndexOf(TYPE_NAME_SEPARATOR);
        if (separator == -1) return Optional.empty();

        var typeAndFlags = keysString.substring(0, separator).strip();
        String inputType;
        var flagSeparator = typeAndFlags.lastIndexOf(' ');
        if (flagSeparator == -1) {
            inputType = typeAndFlags;
        } else {
            inputType = typeAndFlags.substring(flagSeparator + 1).strip();
        }

        var name = keysString.substring(separator + 1);
        var outputType = inputType.equals(INT_KEYWORD) ? I32_KEYWORD : I64_KEYWORD;

        String rendered;
        if (valueSeparator == -1) {
            rendered = renderMagmaDeclaration(name, outputType);
        } else {
            var valueString = input.substring(valueSeparator + VALUE_SEPARATOR.length());
            var mutabilityModifier = input.strip().startsWith(FINAL_KEYWORD) ? CONST_KEYWORD_WITH_SPACE : LET_KEYWORD_WITH_SPACE;

            MapNodeBuilder mapNodeBuilder1 = MapNodeBuilder.Empty.string("mutability-modifier", mutabilityModifier);
            MapNodeBuilder mapNodeBuilder2 = mapNodeBuilder1.string("name", name);
            MapNodeBuilder mapNodeBuilder3 = mapNodeBuilder2.string("type", outputType);
            MapNodeBuilder mapNodeBuilder = mapNodeBuilder3.string("value", valueString);
            rendered = renderMagmaDefinition(mapNodeBuilder.build());
        }

        return Optional.of(rendered);
    }
}
