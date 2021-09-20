package com.meti;

import java.io.IOException;
import java.nio.file.Files;

public class Application {
    private final Script script;

    public Application(Script script) {
        this.script = script;
    }

    void run() throws IOException {
        if (script.exists()) {
            var name = script.extractName();

            var targetHeader = script.extend(name, ".h");
            var targetSource = script.extend(name, ".c");

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
