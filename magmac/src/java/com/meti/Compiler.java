package com.meti;

import java.util.ArrayList;
import java.util.Optional;

import static com.meti.JavaLang.*;
import static com.meti.Lang.*;
import static com.meti.MagmaLang.*;

public class Compiler {

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
        if (input.startsWith(PACKAGE_KEYWORD_WITH_SPACE)) return "";
        if (input.startsWith(IMPORT_KEYWORD)) {
            var truncated = input.substring(IMPORT_KEYWORD.length());
            var segments = truncated.startsWith(STATIC_KEYWORD_WITH_SPACE)
                    ? truncated.substring(STATIC_KEYWORD_WITH_SPACE.length())
                    : truncated;

            var separator = segments.indexOf(JAVA_IMPORT_SEPARATOR);
            var parent = segments.substring(0, separator);
            var child = segments.substring(separator + 1);
            return renderMagmaImport(parent, child);
        }
        return compileClass(input);
    }

    private static String compileClass(String input) {
        var nameStart = input.indexOf(CLASS_KEYWORD_WITH_SPACE) + CLASS_KEYWORD_WITH_SPACE.length();
        var contentStart = input.indexOf(BLOCK_START);
        var contentEnd = input.lastIndexOf(BLOCK_END);
        var inputContent = input.substring(contentStart + BLOCK_START.length(), contentEnd);
        var outputContent = compileClassContent(inputContent);

        var name = input.substring(nameStart, contentStart);
        var modifierString = input.startsWith(PUBLIC_KEYWORD_WITH_SPACE) ? EXPORT_KEYWORD_WITH_SPACE : "";
        return renderMagmaClass(name, modifierString, outputContent);
    }

    private static String compileClassContent(String input) {
        return compileMethod(input)
                .or(() -> compileDefinition(input))
                .orElse("");
    }

    private static Optional<String> compileMethod(String input) {
        if (input.startsWith(VOID_TYPE_WITH_SPACE)) {
            var paramStart = input.indexOf(PARAM_START);
            var paramEnd = input.indexOf(PARAM_END);
            var inputParamContent = input.substring(paramStart + 1, paramEnd);
            String outputParamContent;
            if (inputParamContent.isEmpty()) {
                outputParamContent = "";
            } else {
                var outputParamContentOptional = compileDefinition(inputParamContent);
                if (outputParamContentOptional.isEmpty()) return Optional.empty();
                outputParamContent = outputParamContentOptional.get();
            }

            var name = input.substring(VOID_TYPE_WITH_SPACE.length(), paramStart);
            return Optional.of(renderMagmaFunction("", name, outputParamContent, ""));
        }
        return Optional.empty();
    }

    private static Optional<String> compileDefinition(String input) {
        var valueSeparator = input.indexOf(VALUE_SEPARATOR);
        var keysString = input.substring(0, valueSeparator == -1 ? input.length() : valueSeparator);

        var separator = keysString.indexOf(TYPE_NAME_SEPARATOR);
        if (separator == -1) return Optional.empty();

        var inputType = keysString.substring(0, separator);
        var name = keysString.substring(separator + 1);
        var outputType = inputType.equals(INT_KEYWORD) ? I32_KEYWORD : I64_KEYWORD;

        String rendered;
        if (valueSeparator == -1) {
            rendered = renderMagmaDeclaration(name, outputType);
        } else {
            var valueString = input.substring(valueSeparator + VALUE_SEPARATOR.length(), input.lastIndexOf(STATEMENT_END));
            rendered = renderMagmaDefinition(name, outputType, valueString);
        }
        return Optional.of(rendered);
    }
}
