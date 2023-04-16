package com.meti;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            nodes.add(lex(line));
        }

        var state = new State();
        for (var node : nodes) {
            state = alter(state, node);
        }

        var output = render(state);

        var actualParent = leaf.getParent();
        if (!Files.exists(actualParent)) {
            Files.createDirectories(actualParent);
        }

        Files.writeString(leaf, output);
    }

    private static String render(State state) {
        var output = new StringBuilder();
        var children = state.cache().collectChildren();
        for (var child : children) {
            var name = renderImport(child, 0);
            output.append("import ").append(name).append(";\n");
        }

        for (Node other : state.others()) {
            if (other.is(FunctionNode.Key.Id)) {
                if (other.apply(FunctionNode.Key.Flags)
                        .map(value -> value.contains(FunctionNode.Flag.Class))
                        .orElse(false)) {
                    output.append("class ");
                }

                output.append("def ").append(other.apply(FunctionNode.Key.Name)
                        .flatMap(Attribute::asText)
                        .orElseThrow()).append("() => {}");
            }
        }
        return output.toString();
    }

    private static State alter(State state, Node node) {
        if (node.is(Import.Key.Id)) {
            state.cache().addImport(node.apply(Import.Key.Values)
                    .flatMap(Attribute::asTextList)
                    .orElseThrow());
        } else if (node.is(ClassNode.Key.Id)) {
            state.others().add(new FunctionNode(node.apply(ClassNode.Key.Name)
                    .flatMap(Attribute::asText)
                    .orElseThrow(), Set.of(FunctionNode.Flag.Class)));
        }
        return state;
    }

    private static Node lex(String line) throws CompilationException {
        return Stream.of(new ImportLexer(line),
                        new ClassLexer(line))
                .map(Lexer::lex)
                .flatMap(Optional::stream)
                .findFirst()
                .orElseThrow(() -> new CompilationException("Unknown input: " + line));
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