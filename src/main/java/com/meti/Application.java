package com.meti;

import java.io.IOException;
import java.nio.file.Files;

public class Application {
    private final Source source;

    public Application(Source source) {
        this.source = source;
    }

    void run() throws IOException {
        if (source.exists()) {
            var name = source.extractName();

            var targetHeader = source.extend(name, ".h");
            var targetSource = source.extend(name, ".c");

            var headerContent = "#ifndef " + name + "_h\n" +
                    "#define " + name + "_h\n" +
                    "struct _" + name + "_ {}" +
                    "struct _" + name + "_ __" + name + "__();" +
                    "#endif\n";

            var sourceContent = "struct _" + name + "_ __" + name + "__(){" +
                    "struct _" + name + "_ this={};" +
                    "return this;" +
                    "}";

            Files.writeString(targetHeader, headerContent);
            Files.writeString(targetSource, sourceContent);
        }
    }
}
