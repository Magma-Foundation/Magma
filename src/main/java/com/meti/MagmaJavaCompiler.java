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
        var importOutput = "";
        if (input.startsWith("import native ")) {
            var separator = input.indexOf("from");
            var basesName = input.substring("import native ".length(), separator).trim();
            var bracketStart = basesName.indexOf('{');
            var bracketEnd = basesName.indexOf('}');
            var packageName = input.substring(separator + IMPORT_SEPARATOR + 1, input.length() - 1);
            if (bracketStart == -1 || bracketEnd == -1) {
                return "import %s.%s;class __%s__{}".formatted(packageName, basesName, scriptName);
            } else {
                var childrenString = basesName.substring(bracketStart + 1, bracketEnd).trim();
                var children = childrenString.split(",");
                importOutput = Arrays.stream(children)
                        .map(String::trim)
                        .filter(value -> !value.isEmpty())
                        .map(child -> "import " + packageName + "." + child + ";")
                        .collect(Collectors.joining());
            }
        } else {
            importOutput = "";
        }

        return importOutput + "class __" + scriptName + "__";
    }
}
