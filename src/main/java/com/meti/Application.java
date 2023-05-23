package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Set;

public record Application(Set<Path> sources) {
    static Path resolveFile(String name, String extension) {
        return Paths.get(".", name + "." + extension);
    }

    ArrayList<Path> run() throws IOException {
        var list = new ArrayList<Path>();
        for (var path : sources) {
            if (Files.exists(path)) {
                String output;
                var input = Files.readString(path);
                if (input.startsWith("import ") && input.endsWith(";")) {
                    var name = input.substring("import ".length(), input.indexOf(";"));
                    output = "import " + name + " from '*';";
                } else {
                    output = "";
                }

                var fileName = path.getFileName().toString();
                var separator = fileName.indexOf(".");
                var fileNameWithoutExtension = fileName.substring(0, separator);

                var target = resolveFile(fileNameWithoutExtension, "mgs");
                Files.writeString(target, output);
                list.add(target);
            }
        }

        return list;
    }
}