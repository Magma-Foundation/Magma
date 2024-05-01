package com.meti;

public class Compiler {
    public static final String CLASS_KEYWORD_WITH_SPACE = "class ";
    public static final String CONTENT = " {}";
    public static final String TEST_UPPER_SYMBOL = "Test";
    public static final String PUBLIC_KEYWORD_WITH_SPACE = "public ";
    public static final String EXPORT_KEYWORD_WITH_SPACE = "export ";
    public static final String IMPORT_KEYWORD = "import ";
    public static final String JAVA_IMPORT_SEPARATOR = ".";
    public static final String STATEMENT_END = ";";

    public static String getString(String parent) {
        return IMPORT_KEYWORD + parent + JAVA_IMPORT_SEPARATOR;
    }

    public static String renderJavaImport(String parent, String child) {
        return IMPORT_KEYWORD + parent + JAVA_IMPORT_SEPARATOR + child + STATEMENT_END;
    }

    public static String renderMagmaImport(String parent, String child) {
        return IMPORT_KEYWORD + "{ " + child + " } from " + parent + STATEMENT_END;
    }

    static String renderMagmaFunction(String name) {
        return renderMagmaFunction(name, "");
    }

    static String renderMagmaFunction(String name, String modifierString) {
        return modifierString + CLASS_KEYWORD_WITH_SPACE + "def " + name + "() =>" + CONTENT;
    }

    static String renderJavaClass(String name) {
        return renderJavaClass(name, "");
    }

    static String renderJavaClass(String name, String modifierString) {
        return modifierString + CLASS_KEYWORD_WITH_SPACE + name + CONTENT;
    }

    static String run(String input) {
        var separator = input.indexOf(STATEMENT_END);
        if (separator == -1) {
            return compileClass(input);
        }

        var before = input.substring(0, separator + 1);
        var after = input.substring(separator + 1);

        var newBefore = compileStatement(before);
        return newBefore + compileClass(after);
    }

    private static String compileStatement(String input) {
        if (!input.startsWith(IMPORT_KEYWORD)) return "";
        var segments = input.substring(IMPORT_KEYWORD.length(), input.indexOf(STATEMENT_END));
        var separator = segments.indexOf(JAVA_IMPORT_SEPARATOR);
        var parent = segments.substring(0, separator);
        var child = segments.substring(separator + 1);
        return renderMagmaImport(parent, child);
    }

    private static String compileClass(String input) {
        var nameStart = input.indexOf(CLASS_KEYWORD_WITH_SPACE) + CLASS_KEYWORD_WITH_SPACE.length();
        var nameEnd = input.indexOf(CONTENT);
        var name = input.substring(nameStart, nameEnd);
        var modifierString = input.startsWith(PUBLIC_KEYWORD_WITH_SPACE) ? EXPORT_KEYWORD_WITH_SPACE : "";
        return renderMagmaFunction(name, modifierString);
    }
}
