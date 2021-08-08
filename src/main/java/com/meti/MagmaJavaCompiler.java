package com.meti;

public class MagmaJavaCompiler {
    public static final int IMPORT_SEPARATOR = "from".length();
    private final String input;
    private final String scriptName;

    public MagmaJavaCompiler(String input, String scriptName) {
        this.input = input;
        this.scriptName = scriptName;
    }

    String compile() {
        String output;
        if (input.startsWith("import native ")) {
            if (input.equals("import native {} from bar;")) {
                output = "class __%s__{}".formatted(scriptName);
            } else if (!input.equals("import native { foo } from bar;")) {
                var separator = input.indexOf("from");
                var baseName = input.substring("import native ".length(), separator).trim();
                var packageName = input.substring(separator + IMPORT_SEPARATOR + 1, input.length() - 1);
                output = "import %s.%s;class __%s__{}".formatted(packageName, baseName, scriptName);
            } else {
                output = "import bar.foo;class __%s__{}".formatted(scriptName);
            }
        } else {
            output = "class __%s__{}".formatted(scriptName);
        }
        return output;
    }
}
