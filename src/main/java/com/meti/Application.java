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
        var scriptMacro = "__" + packageName + "_header__";
        var scriptType = "struct __" + packageName + "_type__";
        var scriptMain = "__" + packageName + "_main__";

        var typeSeparator = input.indexOf(':');
        var valueSeparator = input.indexOf('=');
        var typeString = input.substring(typeSeparator + 1, valueSeparator).trim();

        var type = resolveTypeName(typeString);

        var members = input.isBlank() ? "" : "\t" + type + " x;\n";

        var headerContent = renderHeaderWithContent("ifndef", scriptMacro) +
                renderHeaderWithContent("define", scriptMacro) +
                scriptType + " {\n" + members + "}\n" +
                scriptType + " " + scriptMain + "();\n" +
                renderHeader("endif") + "\n";

        var targetContent = scriptType + " " + scriptMain + "(){\n" +
                "\t" + scriptType + " this={};\n" +
                "\treturn this;\n" +
                "}\n";

        return new Output(headerContent, targetContent);
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
