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

    void run() throws IOException {
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

    private Output compile(String packageName, String input) {
        var scriptMacro = "__" + packageName + "_header__";
        var scriptType = "struct __" + packageName + "_type__";
        var scriptMain = "__" + packageName + "_main__";

        var members = input.isBlank() ? "" : "\tint x;\n";

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
}
