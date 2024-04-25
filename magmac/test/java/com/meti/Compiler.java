package com.meti;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Compiler {
    public static final String CLASS_KEYWORD_WITH_SPACE = "class ";
    public static final String CLASS_END = " {}";
    public static final String EXPORT_KEYWORD_WITH_SPACE = "export ";
    public static final String PUBLIC_KEYWORD_WITH_SPACE = "public ";
    public static final String IMPORT_KEYWORD_WITH_SPACE = "import ";
    public static final String STATEMENT_END = ";";

    static String renderMagmaImport(String parent, String child) {
        return IMPORT_KEYWORD_WITH_SPACE + "{ " + child + " } from " + parent + STATEMENT_END;
    }

    static String renderMagmaFunction(String name) {
        return renderMagmaFunction("", name);
    }

    static String renderMagmaFunction(String modifiersString, String name) {
        return modifiersString + CLASS_KEYWORD_WITH_SPACE + "def " + name + "() =>" + CLASS_END;
    }

    static String run(String input) {
        var lines = Arrays.asList(input.split(";"));
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

        var set = beforeString.substring(IMPORT_KEYWORD_WITH_SPACE.length());
        var last = set.lastIndexOf('.');
        var parent = set.substring(0, last);
        var child = set.substring(last + 1);

        return renderMagmaImport(parent, child);
    }

    private static String compileClass(String input) {
        var classIndex = input.indexOf(CLASS_KEYWORD_WITH_SPACE);
        if (classIndex == -1) throw new UnsupportedOperationException("No class present.");

        var name = input.substring(classIndex + CLASS_KEYWORD_WITH_SPACE.length(), input.indexOf(CLASS_END));
        var modifierString = input.startsWith(PUBLIC_KEYWORD_WITH_SPACE) ? EXPORT_KEYWORD_WITH_SPACE : "";

        return renderMagmaFunction(modifierString, name);
    }

    static String renderJavaClass(String name) {
        return renderJavaClass("", name);
    }

    static String renderJavaClass(String modifiersString, String name) {
        return modifiersString + CLASS_KEYWORD_WITH_SPACE + name + CLASS_END;
    }

    static String renderMagmaFunction() {
        return renderMagmaFunction(ApplicationTest.TEST_UPPER_SYMBOL);
    }

    static String renderBeforeClass(String input) {
        return input + renderJavaClass(ApplicationTest.TEST_UPPER_SYMBOL);
    }

    static String renderJavaImport(String parent, String child) {
        return IMPORT_KEYWORD_WITH_SPACE + parent + "." + child + STATEMENT_END;
    }

    static String renderBeforeFunction(String before) {
        return before + renderMagmaFunction();
    }
}
