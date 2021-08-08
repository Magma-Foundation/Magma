package com.meti;

public class MagmaJavaCompiler {
    public static final int IMPORT_SEPARATOR = "from".length();
    private final String input;

    public MagmaJavaCompiler(String input) {
        this.input = input;
    }

    String compile() {
        String output;
        if (input.startsWith("import native ")) {
            var separator = input.indexOf("from");
            var baseName = input.substring("import native ".length(), separator);
            var packageName = input.substring(separator + IMPORT_SEPARATOR, input.length() - 1);
            output = "import %s.%s;class __index__{}".formatted(packageName, baseName);
        } else {
            output = "class __index__{}";
        }
        return output;
    }
}
