package com.meti;

import com.meti.source.Source;
import com.meti.stream.StreamException;

import java.io.IOException;
import java.nio.file.Files;

public class Application {
    private final Source source;

    public Application(Source source) {
        this.source = source;
    }

    void run() throws ApplicationException {
        try {
            source.stream().forEach(this::compileScript);
        } catch (StreamException | IOException e) {
            throw new ApplicationException(e);
        }
    }

    private void compileScript(Script script) throws ApplicationException {
        var name = script.extractName();

        var targetHeader = script.extend(name, ".h");
        var targetSource = script.extend(name, ".c");

        String input;
        try {
            input = script.read();
        } catch (IOException e) {
            throw new ApplicationException(e);
        }

        String importString;
        if (input.isBlank()) {
            importString = "";
        } else if (input.equals("import native stdio;")) {
            importString = "#include <stdio.h>\n";
        } else {
            throw new ApplicationException("Invalid input: " + input);
        }

        var headerContent = "#ifndef " + name + "_h\n" +
                "#define " + name + "_h\n" +
                importString +
                "struct _" + name + "_ {}" +
                "struct _" + name + "_ __" + name + "__();" +
                "#endif\n";

        var sourceContent = "struct _" + name + "_ __" + name + "__(){" +
                "struct _" + name + "_ this={};" +
                "return this;" +
                "}";

        try {
            Files.writeString(targetHeader, headerContent);
            Files.writeString(targetSource, sourceContent);
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }
}
