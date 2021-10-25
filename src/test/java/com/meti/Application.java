package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record Application(Path source) {
    static String renderIncludeDirective() {
        return "#include <stdio.h>\n";
    }

    static String renderNativeImport() {
        return "import native stdio;";
    }

    Path run() throws IOException {
        var fileName = source.getFileName().toString();
        var separator = fileName.indexOf('.');
        var name = fileName.substring(0, separator);
        var targetName = name + ".c";
        var target = source.resolveSibling(targetName);
        if (Files.exists(source)) {
            var input = Files.readString(source);
            String output;
            if (input.equals(renderNativeImport())) {
                output = renderIncludeDirective();
            } else {
                output = "";
            }

            Files.writeString(target, output);
        }

        return target;
    }
}