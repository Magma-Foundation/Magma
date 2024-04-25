package com.meti;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Compiler {
    public static final String CLASS_KEYWORD_WITH_SPACE = "class ";
    public static final String EXPORT_KEYWORD_WITH_SPACE = "export ";
    public static final String PUBLIC_KEYWORD_WITH_SPACE = "public ";
    public static final String IMPORT_KEYWORD_WITH_SPACE = "import ";
    public static final String STATEMENT_END = ";";
    public static final String STATIC_KEYWORD_WITH_SPACE = "static ";
    public static final String BLOCK_START = "{";
    public static final String BLOCK_END = "}";
    public static final String INT_KEYWORD = "int";
    public static final String I32_KEYWORD = "I32";
    public static final String LONG_KEYWORD = "long";
    public static final String I64_KEYWORD = "I64";
    public static final String VALUE_SEPARATOR = "=";
    public static final String DEFINITION_END = " " + VALUE_SEPARATOR + " 0;";

    public static String renderJavaDefinition(String name, String type) {
        return type + " " + name + DEFINITION_END;
    }

    public static String renderMagmaDefinition(String name, String type) {
        return "let " + name + " : " + type + DEFINITION_END;
    }

    private static String renderBlock(String content) {
        return BLOCK_START + content + BLOCK_END;
    }

    static String renderMagmaImport(String parent, String child) {
        return IMPORT_KEYWORD_WITH_SPACE + "{ " + child + " } from " + parent + STATEMENT_END;
    }

    static String renderMagmaFunction(String name) {
        return renderMagmaFunction("", name);
    }

    static String renderMagmaFunction(String modifiersString, String name) {
        return renderMagmaFunction(modifiersString, name, "");
    }

    static String renderMagmaFunction(String modifiersString, String name, String content) {
        return modifiersString + CLASS_KEYWORD_WITH_SPACE + "def " + name + "() =>" + " " + renderBlock(content);
    }

    static String run(String input) {
        var lines = split(input);
        var imports = lines.subList(0, lines.size() - 1);
        var classString = lines.get(lines.size() - 1);

        var beforeString = imports.stream()
                .map(Compiler::compileImport)
                .collect(Collectors.joining());

        var compiledClass = compileClass(classString);
        return beforeString + compiledClass;
    }

    private static ArrayList<String> split(String input) {
        var lines = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;
        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (c == ';' && depth == 0) {
                lines.add(builder.toString());
                builder = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
                builder.append(c);
            }
        }

        lines.add(builder.toString());
        return lines;
    }

    private static String compileImport(String beforeString) {
        if (!beforeString.startsWith(IMPORT_KEYWORD_WITH_SPACE)) return "";
        var segmentStart = beforeString.startsWith(IMPORT_KEYWORD_WITH_SPACE + STATIC_KEYWORD_WITH_SPACE)
                ? (IMPORT_KEYWORD_WITH_SPACE + STATIC_KEYWORD_WITH_SPACE).length()
                : IMPORT_KEYWORD_WITH_SPACE.length();

        var set = beforeString.substring(segmentStart);
        var last = set.lastIndexOf('.');
        var parent = set.substring(0, last);
        var child = set.substring(last + 1);

        return renderMagmaImport(parent, child);
    }

    private static String compileClass(String input) {
        var classIndex = input.indexOf(CLASS_KEYWORD_WITH_SPACE);
        if (classIndex == -1) throw new UnsupportedOperationException("No class present.");

        var nameStart = classIndex + CLASS_KEYWORD_WITH_SPACE.length();
        var contentStart = input.indexOf(BLOCK_START);
        var contentEnd = input.lastIndexOf(BLOCK_END);

        var className = input.substring(nameStart, contentStart).strip();
        var modifierString = input.startsWith(PUBLIC_KEYWORD_WITH_SPACE) ? EXPORT_KEYWORD_WITH_SPACE : "";

        var inputContent = input.substring(contentStart + 1, contentEnd);
        var outputContent = compileDefinition(inputContent);

        return renderMagmaFunction(modifierString, className, outputContent);
    }

    private static String compileDefinition(String inputContent) {
        if (!inputContent.contains(VALUE_SEPARATOR)) return "";

        var before = inputContent.substring(0, inputContent.indexOf(DEFINITION_END));
        var separator = before.lastIndexOf(' ');
        var inputType = before.substring(0, separator);
        var name = before.substring(separator + 1);
        var outputType = compileType(inputType);

        return renderMagmaDefinition(name, outputType);
    }

    private static String compileType(String inputType) {
        String outputType;
        if (inputType.equals(INT_KEYWORD)) {
            outputType = I32_KEYWORD;
        } else if (inputType.equals(LONG_KEYWORD)) {
            outputType = I64_KEYWORD;
        } else {
            outputType = inputType;
        }
        return outputType;
    }

    static String renderJavaClass(String name) {
        return renderJavaClass("", name);
    }

    static String renderJavaClass(String modifiersString, String name) {
        return renderJavaClass(modifiersString, name, "");
    }

    static String renderJavaClass(String modifiersString, String name, String content) {
        return modifiersString + CLASS_KEYWORD_WITH_SPACE + name + " " + renderBlock(content);
    }

    static String renderJavaImport(String parent, String child) {
        return renderJavaImport(parent, child, "");
    }

    static String renderJavaImport(String parent, String child, String modifierString) {
        return IMPORT_KEYWORD_WITH_SPACE + modifierString + parent + "." + child + STATEMENT_END;
    }

    static String renderBeforeFunction(String before) {
        return before + ApplicationTest.renderMagmaFunction();
    }
}
