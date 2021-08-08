package com.meti;

import java.util.Arrays;
import java.util.stream.Collectors;

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
            } else {
                var separator = input.indexOf("from");
                var basesName = input.substring("import native ".length(), separator).trim();
                var bracketStart = basesName.indexOf('{');
                var bracketEnd = basesName.indexOf('}');
                var packageName = input.substring(separator + IMPORT_SEPARATOR + 1, input.length() - 1);
                if (bracketStart == -1 || bracketEnd == -1) {
                    output = "import %s.%s;class __%s__{}".formatted(packageName, basesName, scriptName);
                } else {
                    var childrenString = basesName.substring(bracketStart + 1, bracketEnd).trim();
                    var children = childrenString.split(",");
                    var newChildren = Arrays.stream(children)
                            .map(String::trim)
                            .map(child -> "import " + packageName + "." + child)
                            .collect(Collectors.joining(";"));
                    output = "%s;class __%s__{}".formatted(newChildren, scriptName);
                }
            }
        } else {
            output = "class __%s__{}".formatted(scriptName);
        }
        return output;
    }
}
