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

    private static String renderBlock() {
        return renderBlock("");
    }

    private static String renderBlock(String content) {
        return BLOCK_START + content + "}";
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
        var lines = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;
        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if(c == ';' && depth == 0) {
                lines.add(builder.toString());
                builder = new StringBuilder();
            } else {
                if(c == '{') depth++;
                if(c == '}') depth--;
                builder.append(c);
            }
        }

        lines.add(builder.toString());

        if (lines.isEmpty()) return "";

        var imports = lines.subList(0, lines.size() - 1);
        var classString = lines.get(lines.size() - 1);

        var beforeString = imports.stream()
                .map(Compiler::compileImport)
                .collect(Collectors.joining());

        var compiledClass = compileClass(classString);
        return beforeString + compiledClass;
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
        var nameEnd = input.indexOf(BLOCK_START);
        var name = input.substring(nameStart, nameEnd).strip();
        var modifierString = input.startsWith(PUBLIC_KEYWORD_WITH_SPACE) ? EXPORT_KEYWORD_WITH_SPACE : "";

        return renderMagmaFunction(modifierString, name);
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
