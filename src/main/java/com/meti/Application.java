package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Application {
    private final Path source;

    public Application(Path source) {
        this.source = source;
    }

    private static String renderHeader(String name) {
        return "#" + name;
    }

    private static String renderHeaderWithContent(String name, String content) {
        return renderHeader(name) + " " + content + "\n";
    }

    void run() throws IOException {
        if (Files.exists(source)) {
            var fileName = source.getFileName();
            var fileNameString = fileName.toString();
            var separator = fileNameString.indexOf('.');
            var fileNameWithoutSeparator = fileNameString.substring(0, separator);

            Files.createFile(source.resolveSibling(fileNameWithoutSeparator + ".c"));

            var headerMacro = "__" + fileNameWithoutSeparator + "_header__";
            Files.writeString(source.resolveSibling(fileNameWithoutSeparator + ".h"),
                    renderHeaderWithContent("ifndef", headerMacro) +
                            renderHeaderWithContent("define", headerMacro) +
                            "struct __" + fileNameWithoutSeparator + "_type__ {}\n" +
                            renderHeader("endif") + "\n");
        }
    }
}
