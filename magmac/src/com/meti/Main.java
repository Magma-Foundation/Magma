package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static com.meti.Options.$Option;

public class Main {
    public static void main(String[] args) {
        var root = Paths.get(".", "magmac");
        var source = root.resolve("src");
        var dist = root.resolve("dist");

        if (!Files.exists(dist)) {
            try {
                Files.createDirectories(dist);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (var list = Files.walk(source)) {
            list.filter(Files::isRegularFile).forEach(file -> compileFile(source, dist, file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void compileFile(Path source, Path dist, Path file) {
        try {
            var input = Files.readString(file);
            var lines = new Splitter(input).split();

            var output = lines.stream()
                    .map(Main::compileLine)
                    .collect(Collectors.joining());

            var relative = source.relativize(file);
            var parent = relative.getParent();

            var fileName = relative.getFileName().toString();
            var separator = fileName.indexOf('.');
            var withoutSeparator = fileName.substring(0, separator);

            var resolvedParent = dist.resolve(parent);
            if (!Files.exists(resolvedParent)) {
                Files.createDirectories(resolvedParent);
            }

            var outputFile = resolvedParent.resolve(withoutSeparator + ".mgs");
            Files.writeString(outputFile, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String compileLine(String input) {
        var stripped = new JavaString(input.strip());
        return compilePackage(stripped)
                .orElseGet(() -> compileImport(stripped))
                .orElseGet(() -> compileBlock(stripped))
                .orElseGet(() -> compileClass(stripped))
                .orElseGet(() -> compileRecord(stripped))
                .unwrapOrElseGet(() -> input);
    }

    private static Option<String> compilePackage(JavaString stripped) {
        return stripped.firstIndexOfSlice("package ")
                .filter(Index::isStart)
                .replaceValue("");
    }

    private static Option<String> compileImport(JavaString stripped) {
        return stripped.firstIndexOfSlice("import ").map(index -> compileValidImport(stripped, index));
    }

    private static Option<String> compileRecord(JavaString stripped) {
        return $Option(() -> {
            var nameStart = stripped.firstIndexOfSlice("record ").$()
                    .nextBy("record ".length())
                    .$();

            var paramStart = stripped.firstIndexOfChar('(').$();
            var bodyStart = stripped.firstIndexOfChar('{').$();
            var bodyEnd = stripped.lastIndexOfChar('}').$()
                    .next()
                    .$();

            var name = stripped.slice(nameStart.to(paramStart).$());
            var bodySlice = stripped.slice(bodyStart.to(bodyEnd).$());
            var compiledBody = compileLine(bodySlice);
            return new MethodNode(name, "()", compiledBody).renderMethod();
        });
    }

    private static Option<String> compileClass(JavaString stripped) {
        return $Option(() -> {
            if (!stripped.contains("class ")) {
                return Options.$$();
            }

            var bodyStart = stripped.firstIndexOfChar('{').$();
            var bodyEnd = stripped
                    .lastIndexOfChar('}').$()
                    .next().$();

            var extendsIndex = stripped.firstIndexOfSlice("extends ");
            var name = extendsIndex.map(extendsIndex1 -> {
                var keys = stripped.sliceTo(extendsIndex1).strip();
                var separator = keys.lastIndexOf(' ');
                return keys.substring(separator + 1).strip();
            }).unwrapOrElseGet(() -> {
                var keys = stripped.sliceTo(bodyStart).strip();
                var separator = keys.lastIndexOf(' ');
                return keys.substring(separator + 1).strip();
            });

            var body = stripped.slice(bodyStart.to(bodyEnd).$()).strip();

            var classNode = new ClassNode(name, body);
            var outputBody = compileLine(classNode.body());
            return new MethodNode(classNode.name(), "", outputBody).renderMethod();
        });
    }

    private static Option<String> compileBlock(JavaString body) {
        return $Option(() -> {
            var bodyStart = body.firstIndexOfChar('{')
                    .filter(Index::isStart)
                    .$()
                    .next()
                    .$();

            var bodyEnd = body.lastIndexOfChar('}')
                    .filter(Index::isEnd)
                    .$();

            var slicedBody = body.slice(bodyStart.to(bodyEnd).$()).strip();
            var collect1 = new Splitter(slicedBody).split();
            return new BlockNode(collect1);
        }).map(node -> {
            var collect = node
                    .lines()
                    .stream()
                    .map(Main::compileLine)
                    .collect(Collectors.toList());
            return new BlockNode(collect);
        }).map(node -> new BlockRenderer(node).render());
    }

    private static String compileValidImport(JavaString stripped, Index index) {
        var name = stripped.slice(index);
        var separator = name.lastIndexOf('.');
        var parent = name.substring(0, separator);
        var child = name.substring(separator + 1);
        return new ImportRenderer(new ImportNode(parent, child)).render();
    }
}