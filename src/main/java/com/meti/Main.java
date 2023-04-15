package com.meti;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
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

        var imports = new ArrayList<List<String>>();
        for (String line : lines) {
            if (line.startsWith("import ")) {
                var importSlice = line.substring("import ".length());
                var args = Arrays.stream(importSlice.split("\\."))
                        .map(String::strip)
                        .collect(Collectors.toList());

                imports.add(args);
            }
        }

        var cache = parse(imports);

        var output = new StringBuilder();
        var root = cache.getRoot();
        var children = root.children();
        for (var child : children) {
            var name = renderImport(child, 0);
            output.append("import " + name + ";");
        }

        var actualParent = leaf.getParent();
        if (!Files.exists(actualParent)) {
            Files.createDirectories(actualParent);
        }

        Files.writeString(leaf, output.toString());
    }

    private static String renderImport(ImportCache.Import value, int depth) {
        var name = value.computeName();
        if (value.isLeaf()) {
            return name;
        }

        if (value.children().size() == 1) {
            var anyChild = value.children().stream().findFirst().orElseThrow();
            var newName = name + "." + anyChild.name();
            var newImport = new ImportCache.Import(newName, anyChild.children());
            return renderImport(newImport, depth);
        }

        var outerOffset = "\t".repeat(depth);
        var innerOffset = "\t".repeat(depth + 1);
        var renderedChildren = value.children()
                .stream()
                .map(value1 -> renderImport(value1, depth + 1))
                .collect(Collectors.joining(",\n" + innerOffset));

        var isParentToLeaves = value.children().stream().allMatch(ImportCache.Import::isLeaf);

        String beforeChildren;
        if (isParentToLeaves) {
            beforeChildren = "";
        } else {
            beforeChildren = "\n" + innerOffset;
        }

        String afterChildren;
        if (isParentToLeaves) {
            afterChildren = " ";
        } else {
            afterChildren = "\n" + outerOffset;
        }

        String afterChildrenClose;
        if (isParentToLeaves) {
            afterChildrenClose = "";
        } else {
            afterChildrenClose = "";
        }

        return "{" + beforeChildren + renderedChildren + afterChildren + "}" + afterChildrenClose + " from " + name;
    }

    private static ImportCache parse(List<List<String>> imports) {
        var cache = new ImportCache();
        for (var anImport : imports) {
            cache.addImport(anImport);
        }

        return cache;
    }

    private static String computeFileName(Path relativeOriginal) {
        var fileName = relativeOriginal.getFileName().toString();
        var separator = fileName.indexOf(".");
        var fileNameWithoutExtension = fileName.substring(0, separator);
        return fileNameWithoutExtension;
    }
}