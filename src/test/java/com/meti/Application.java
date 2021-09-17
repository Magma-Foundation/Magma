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
            String fileName = separator == -1 ?
                    fileNameWithExtension :
                    fileNameWithExtension.substring(0, separator);
            var target = source.resolveSibling(fileName);
            Files.createFile(target);
        }
    }
}