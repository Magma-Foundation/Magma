package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Application {
    static void run(Path source) throws IOException {
        if (Files.exists(source)) {
            var name = source.getFileName();
            var fileNameWithExtension = name.getName(name.getNameCount() - 1)
                .toString();
            var separator = fileNameWithExtension.indexOf('.');
            var packageName = separator == -1 ?
                    fileNameWithExtension :
                    fileNameWithExtension.substring(0, separator);
            var target = source.resolveSibling(packageName + ".c");
            var input = Files.readString(source);
            var output = compile(packageName, input);
            Files.writeString(target, output);
        }
    }

    private static String compile(String packageName, String input) {
        String output;
        if (input.isEmpty()) {
            output =  "";
        } else {
            var classType = "___" + packageName + "___";
            var structType = "struct " + classType;
            var rawName = input.substring("const ".length(), input.indexOf(':'));
            var name = rawName.trim();
            output = structType + "{};" +
                     structType + " __" + packageName + "__(){" +
                     structType + " this={};int " + name + "=10;return this;}";
        }
        return output;
    }
}