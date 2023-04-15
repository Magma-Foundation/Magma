package com.meti;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        var files = new ArrayList<Path>();
        var sourceDirectory = Paths.get(".", "src");
        Files.walkFileTree(sourceDirectory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                files.add(file);
                return FileVisitResult.CONTINUE;
            }
        });

        var target = Paths.get(".", "target");
        for (var file : files) {
            compile(sourceDirectory, target, file);
        }
    }

    private static void compile(Path sourceDirectory, Path target, Path file) throws IOException {
        var relativeOriginal = sourceDirectory.relativize(file);
        var fileNameWithoutExtension = computeFileName(relativeOriginal);

        Path leafDirectory;
        var parent = relativeOriginal.getParent();
        if (parent == null) {
            leafDirectory = target;
        } else {
            leafDirectory = target.resolve(parent);
        }

        var leaf = leafDirectory
                .resolve(fileNameWithoutExtension + ".mgs");

        var input = Files.readString(file);
        var lines = Arrays.stream(input.split(";"))
                .map(String::strip)
                .filter(value -> !value.isEmpty())
                .toList();

        var imports = new HashMap<String, List<String>>();
        var output = new StringBuilder();
        for (String line : lines) {
            if (line.startsWith("import ")) {
                var importSlice = line.substring("import ".length());
                var separator = importSlice.indexOf(".");
                var importParent = importSlice.substring(0, separator);

                List<String> children;
                if (imports.containsKey(importParent)) {
                    children = imports.get(importParent);
                } else {
                    children = new ArrayList<>();
                }

                children.add(importSlice.substring(separator + 1));
                imports.put(importParent, children);
            }
        }

        for (String s : imports.keySet()) {
            var joinedChildren = imports.get(s)
                    .stream()
                    .map(value -> "\t" + value)
                    .collect(Collectors.joining(",\n"));

            output.append("import {\n")
                    .append(joinedChildren)
                    .append("\n} from ")
                    .append(s)
                    .append(";");
        }

        var actualParent = leaf.getParent();
        if (!Files.exists(actualParent)) {
            Files.createDirectories(actualParent);
        }

        Files.writeString(leaf, output.toString());
    }

    private static String computeFileName(Path relativeOriginal) {
        var fileName = relativeOriginal.getFileName().toString();
        var separator = fileName.indexOf(".");
        var fileNameWithoutExtension = fileName.substring(0, separator);
        return fileNameWithoutExtension;
    }
}