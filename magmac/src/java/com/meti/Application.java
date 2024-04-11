package com.meti;

public class Application {
    public static final String CLASS_KEYWORD = "class ";
    public static final String JAVA_CLASS_BODY = " {}";
    public static final String DEF_KEYWORD = "def ";
    public static final String MAGMA_FUNCTION_BODY = "() => {}";
    public static final String EXPORT_KEYWORD = "export ";
    public static final String PUBLIC_KEYWORD = "public ";

    static String renderJavaClass(String name) {
        return CLASS_KEYWORD + name + JAVA_CLASS_BODY;
    }

    static String renderMagmaFunction(String name) {
        return CLASS_KEYWORD + DEF_KEYWORD + name + MAGMA_FUNCTION_BODY;
    }

    static String compileJavaToMagma(String input) throws CompileException {
        var classIndex = input.indexOf(CLASS_KEYWORD);
        if (classIndex == -1) throw createUnknownInputError(input);

        var name = input.substring(classIndex + CLASS_KEYWORD.length(), input.indexOf(JAVA_CLASS_BODY));
        var rendered = renderMagmaFunction(name);

        return input.startsWith(PUBLIC_KEYWORD) ? EXPORT_KEYWORD + rendered : rendered;
    }

    static CompileException createUnknownInputError(String input) {
        return new CompileException("Unknown input: " + input);
    }

    static String compileMagmaToJava(String input) throws CompileException {
        var prefixIndex = input.indexOf(CLASS_KEYWORD + DEF_KEYWORD);
        if (prefixIndex == -1) throw createUnknownInputError(input);

        var name = input.substring(prefixIndex + (CLASS_KEYWORD + DEF_KEYWORD).length(), input.indexOf(MAGMA_FUNCTION_BODY));
        var rendered = renderJavaClass(name);

        return input.startsWith(EXPORT_KEYWORD) ? PUBLIC_KEYWORD + rendered : rendered;
    }
}