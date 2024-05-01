package com.meti;

import java.util.ArrayList;

import static com.meti.Lang.*;

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
        return renderMagmaFunction(name, modifierString, outputContent);
    }

    private static String compileClassContent(String input) {
        if (input.startsWith(INT_KEYWORD_WITH_SPACE)) {
            var name = input.substring(INT_KEYWORD_WITH_SPACE.length(), input.lastIndexOf(JAVA_DEFINITION_END));
            return renderMagmaDefinition(name);
        }
        return "";
    }
}
