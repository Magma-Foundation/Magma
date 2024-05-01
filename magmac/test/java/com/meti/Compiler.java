package com.meti;

public class Compiler {
    public static final String CLASS_KEYWORD_WITH_SPACE = "class ";
    public static final String CONTENT = " {}";
    public static final String TEST_UPPER_SYMBOL = "Test";
    public static final String PUBLIC_KEYWORD_WITH_SPACE = "public ";
    public static final String EXPORT_KEYWORD_WITH_SPACE = "export ";
    public static final String JAVA_IMPORT = "import parent.Child;";
    public static final String MAGMA_IMPORT = "import { Child } from parent;";

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
        var separator = input.indexOf(';');
        if (separator == -1) {
            return compileClass(input);
        } else {
            var before = input.substring(0, separator + 1);
            var after = input.substring(separator + 1);

            var newBefore = computeNewBefore(before);
            return newBefore + compileClass(after);
        }
    }

    private static String computeNewBefore(String before) {
        String newBefore;
        if (before.equals(JAVA_IMPORT)) {
            newBefore = MAGMA_IMPORT;
        } else {
            newBefore = "";
        }
        return newBefore;
    }

    private static String compileClass(String input) {
        var nameStart = input.indexOf(CLASS_KEYWORD_WITH_SPACE) + CLASS_KEYWORD_WITH_SPACE.length();
        var nameEnd = input.indexOf(CONTENT);
        var name = input.substring(nameStart, nameEnd);
        var modifierString = input.startsWith(PUBLIC_KEYWORD_WITH_SPACE) ? EXPORT_KEYWORD_WITH_SPACE : "";
        return renderMagmaFunction(name, modifierString);
    }
}
