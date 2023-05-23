package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public record Application(Set<Path> sources) {
    static Path resolveFile(String name, String extension) {
        return Paths.get(".", name + "." + extension);
    }

    ArrayList<Path> run() throws IOException {
        var list = new ArrayList<Path>();
        for (var path : sources) {
            if (Files.exists(path)) {
                var input = Files.readString(path);

                var imports = new HashMap<String, List<String>>();
                var lines = input.split(";");
                for (String line : lines) {
                    var trimmed = line.strip();
                    if (trimmed.startsWith("import ")) {
                        var name = trimmed.substring("import ".length());

                        var args = Arrays.asList(name.split("\\."));
                        String parent;
                        String child;
                        if (args.size() == 1) {
                            parent = "*";
                            child = args.get(0);
                        } else {
                            parent = String.join(".", args.subList(0, args.size() - 1));
                            child = args.get(args.size() - 1);
                        }

                        if (!imports.containsKey(parent)) {
                            imports.put(parent, new ArrayList<>());
                        }
                        imports.get(parent).add(child);
                    }
                }

                String output = "";
                if (!imports.isEmpty()) {
                    for (Map.Entry<String, List<String>> stringListEntry : imports.entrySet()) {
                        String values;
                        if (stringListEntry.getValue().size() == 1) {
                            values = stringListEntry.getValue().get(0);
                        } else {
                            values = "{ " + String.join(", ", stringListEntry.getValue()) + " }";
                        }

                        output = "import " + values + " from " + stringListEntry.getKey() + ";";
                    }
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