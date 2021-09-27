package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record Application(Path source) {
    static String formatTargetName(String packageName) {
        return packageName + ".c";
    }

    void run() throws IOException {
        if (Files.exists(source)) {
            var fileName = source.getFileName().toString();
            var separator = fileName.indexOf(".");
            var packageName = fileName.substring(0, separator);
            var targetName = formatTargetName(packageName);
            Files.createFile(source.resolveSibling(targetName));
        }
    }
}
