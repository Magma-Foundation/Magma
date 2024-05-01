package com.meti;

import java.util.ArrayList;

public class Compiler {

    static String run(String input) {
        var statements = new ArrayList<String>();
        var inputBuilder = new StringBuilder();
        var depth = 0;

        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (c == Lang.STATEMENT_END && depth == 0) {
                statements.add(inputBuilder.toString());
                inputBuilder = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
                inputBuilder.append(c);
            }
        }
        statements.add(inputBuilder.toString());

        var outputBuilder = new StringBuilder();
        for (String statement : statements) {
            outputBuilder.append(compileStatement(statement));
        }
        return outputBuilder.toString();
    }

    private static String compileStatement(String input) {
        if (input.startsWith(Lang.PACKAGE_KEYWORD_WITH_SPACE)) return "";
        if (input.startsWith(Lang.IMPORT_KEYWORD)) {
            var truncated = input.substring(Lang.IMPORT_KEYWORD.length());
            var segments = truncated.startsWith(Lang.STATIC_KEYWORD_WITH_SPACE)
                    ? truncated.substring(Lang.STATIC_KEYWORD_WITH_SPACE.length())
                    : truncated;

            var separator = segments.indexOf(Lang.JAVA_IMPORT_SEPARATOR);
            var parent = segments.substring(0, separator);
            var child = segments.substring(separator + 1);
            return Lang.renderMagmaImport(parent, child);
        }
        return compileClass(input);
    }

    private static String compileClass(String input) {
        var nameStart = input.indexOf(Lang.CLASS_KEYWORD_WITH_SPACE) + Lang.CLASS_KEYWORD_WITH_SPACE.length();
        var contentStart = input.indexOf(Lang.BLOCK_START);
        var contentEnd = input.lastIndexOf(Lang.BLOCK_END);
        var inputContent = input.substring(contentStart + Lang.BLOCK_START.length(), contentEnd);
        var outputContent = inputContent.equals(Lang.JAVA_DEFINITION) ? Lang.MAGMA_DEFINITION : "";

        var name = input.substring(nameStart, contentStart);
        var modifierString = input.startsWith(Lang.PUBLIC_KEYWORD_WITH_SPACE) ? Lang.EXPORT_KEYWORD_WITH_SPACE : "";
        return Lang.renderMagmaFunction(name, modifierString, outputContent);
    }
}
