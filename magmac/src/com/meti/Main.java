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
                .orElseGet(() -> new ImportLexer(stripped)
                        .lex()
                        .map(Main::renderNode))
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

    private static String renderNode(Node node) {
        return enumerateRenderers(node)
                .map(Renderer::render)
                .head()
                .flatMap(value -> value)
                .unwrapOrElseGet(() -> "");
    }

    private static Iterator<Renderer> enumerateRenderers(Node node) {
        return Iterators.from(
                new ImportRenderer(node),
                new BlockRenderer(node)
        );
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

            return getMethodNode(new MethodNode(name, "()", bodySlice));
        }).map(node -> new FunctionRenderer(node).render().unwrapOrElseGet(() -> ""));
    }

    private static MethodNode getMethodNode(MethodNode methodNode) {
        var compiledBody = compileLine(methodNode.body());
        return new MethodNode(methodNode.name(), "()", compiledBody);
    }

    private static Option<String> compileClass(JavaString stripped) {
        return lexClass(stripped).map(node -> {
            var outputBody = compileLine(node.body());
            return new MethodNode(node.name(), "", outputBody);
        }).map(node1 -> new FunctionRenderer(node1).render().unwrapOrElseGet(() -> ""));
    }

    private static Option<ClassNode> lexClass(JavaString stripped) {
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
            return new ClassNode(name, body);
        });
    }

    private static Option<String> compileBlock(JavaString body) {
        return new BlockLexer(body)
                .lex()
                .map(Main::transform)
                .map(Main::renderNode);
    }

    private static Node transform(Node node) {
        return node.getLines().<Node>map(lines -> {
            var collect = lines
                    .stream()
                    .map(Main::compileLine)
                    .collect(Collectors.toList());
            return new BlockNode(collect);
        }).unwrapOrElse(node);
    }
}