package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Application {
    private final Path source;

    public Application(Path source) {
        this.source = source;
    }

    void run() throws IOException {
        if (Files.exists(source)) {
            var name = extractNameWithoutExtension();

            var targetHeader = source.resolveSibling(name + ".h");
            var targetSource = source.resolveSibling(name + ".c");

            Files.writeString(targetHeader, "#ifndef " + name + "_h\n" +
                    "#define " + name + "_h\n" +
                    "struct _" + name + "_ {}" +
                    "struct _" + name + "_ __" + name + "__();" +
                    "#endif\n");

            Files.writeString(targetSource, "struct _" + name + "_ __" + name + "__(){" +
                    "struct _" + name + "_ this={};" +
                    "return this;" +
                    "}");
        }
    }

    private String extractNameWithoutExtension() {
        var nativeName = source.getName(source.getNameCount() - 1)
                .toString();
        var separator = nativeName.indexOf('.');
        String name;
        if (separator == -1) {
            name = nativeName;
        } else {
            name = nativeName.substring(0, separator);
        }
        return name;
    }
}
