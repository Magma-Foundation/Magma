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
            try {
                compile(sourceDirectory, target, file);
            } catch (CompilationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void compile(Path sourceDirectory, Path target, Path file) throws IOException, CompilationException {
        var relativeOriginal = sourceDirectory.relativize(file);
        var fileNameWithoutExtension = computeFileName(relativeOriginal);

        Path leafDirectory;
        var parent = relativeOriginal.getParent();
        if (parent == null) {
            leafDirectory = target;
        } else {
            leafDirectory = target.resolve(parent);
        }

        var leaf = leafDirectory.resolve(fileNameWithoutExtension + ".mgs");

        var input = Files.readString(file);
        var lines = split(input);

        var nodes = new ArrayList<Node>();
        for (var line : lines) {
            if (line.startsWith("import ")) {
                var importSlice = line.substring("import ".length());
                var args = Arrays.stream(importSlice.split("\\."))
                        .map(String::strip)
                        .collect(Collectors.toList());

                nodes.add(new Import(args));
            } else if (line.contains("class")) {
                var name = line.substring(line.indexOf("class") + "class".length(), line.indexOf('{')).strip();
                nodes.add(new ClassNode(name));
            } else {
                throw new CompilationException("Unknown input: " + line);
            }
        }

        var cache = new ImportCache();
        var others = new ArrayList<Node>();
        for (var node : nodes) {
            if (node.is(Import.Key.Id)) {
                cache.addImport(node.apply(Import.Key.Values)
                        .flatMap(Attribute::asTextList)
                        .orElseThrow());
            } else if (node.is(ClassNode.Key.Id)) {
                others.add(new Struct(node.apply(ClassNode.Key.Name)
                        .flatMap(Attribute::asText)
                        .orElseThrow()));
            }
        }

        var output = new StringBuilder();
        var children = cache.collectChildren();
        for (var child : children) {
            var name = renderImport(child, 0);
            output.append("import ").append(name).append(";\n");
        }

        for (Node other : others) {
            if (other.is(Struct.Key.Id)) {
                output.append("struct ").append(other.apply(Struct.Key.Name)
                        .flatMap(Attribute::asText)
                        .orElseThrow()).append(" {}");
            }
        }

        var actualParent = leaf.getParent();
        if (!Files.exists(actualParent)) {
            Files.createDirectories(actualParent);
        }

        Files.writeString(leaf, output.toString());
    }

    private static List<String> split(String input) {
        var builder = new StringBuilder();
        var lines = new ArrayList<String>();
        var depth = 0;
        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (c == ';' && depth == 0) {
                lines.add(builder.toString().strip());
                builder = new StringBuilder();
            } else if (c == '}' && depth == 1) {
                depth--;
                builder.append(c);

                lines.add(builder.toString().strip());
                builder = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
                builder.append(c);
            }
        }

        lines.add(builder.toString().strip());
        lines.removeIf(String::isEmpty);

        return lines;
    }

    private static String renderImport(CachedImport value, int depth) {
        var name = value.computeName();
        if (value.isLeaf()) {
            return name;
        }

        if (value.children().size() == 1) {
            var anyChild = value.children().stream().findFirst().orElseThrow();
            var newName = name + "." + anyChild.computeName();
            var newImport = new CachedImport(newName, anyChild.children());
            return renderImport(newImport, depth);
        }

        var outerOffset = "\t".repeat(depth);
        var innerOffset = "\t".repeat(depth + 1);
        var renderedChildren = value.children()
                .stream()
                .map(value1 -> renderImport(value1, depth + 1))
                .collect(Collectors.joining(",\n" + innerOffset));

        var isParentToLeaves = value.children().stream().allMatch(CachedImport::isLeaf);

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

    private static String computeFileName(Path relativeOriginal) {
        var fileName = relativeOriginal.getFileName().toString();
        var separator = fileName.indexOf(".");
        var fileNameWithoutExtension = fileName.substring(0, separator);
        return fileNameWithoutExtension;
    }
}