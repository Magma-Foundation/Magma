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

    private static void write(Path source, String packageName, Output output) throws IOException {
        Files.writeString(source.resolveSibling(packageName + ".h"), output.getHeaderContent());
        Files.writeString(source.resolveSibling(packageName + ".c"), output.getTargetContent());
    }

    void run() throws IOException, ApplicationException {
        if (Files.exists(source)) {
            var fileName = source.getFileName();
            var fileNameString = fileName.toString();
            var separator = fileNameString.indexOf('.');
            var packageName = fileNameString.substring(0, separator);
            var input = Files.readString(source);
            var output = compile(packageName, input);
            write(source, packageName, output);
        }
    }

    private Output compile(String packageName, String input) throws ApplicationException {
        return new Output("", new Compiler(input).compile());
    }

    private String resolveTypeName(String typeString) throws ApplicationException {
        String type;
        if (typeString.equals("U16")) {
            type = "unsigned int";
        } else if (typeString.equals("I16")) {
            type = "int";
        } else {
            throw new ApplicationException("Unknown type: " + typeString);
        }
        return type;
    }
}
