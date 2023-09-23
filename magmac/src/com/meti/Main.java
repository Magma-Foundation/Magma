package com.meti;

import com.meti.api.collect.JavaString;
import com.meti.compile.JavaLexer;
import com.meti.compile.MagmaRenderer;
import com.meti.compile.Node;
import com.meti.compile.Splitter;
import com.meti.compile.block.BlockNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

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
        return new JavaLexer(stripped)
                .lex()
                .map(Main::transform)
                .map(Main::renderNode)
                .unwrapOrElseGet(() -> input);
    }

    private static String renderNode(Node node) {
        return new MagmaRenderer(node).render().unwrapOrElseGet(() -> "");
    }

    private static Node transform(Node node) {
        var withBody = node.getBody().map(body -> {
            var compiledBody = compileLine(body);
            return node.withBody(compiledBody);
        }).unwrapOrElse(node);

        return withBody.getLines().<Node>map(lines -> {
            var collect = lines
                    .stream()
                    .map(Main::compileLine)
                    .collect(Collectors.toList());
            return new BlockNode(collect);
        }).unwrapOrElse(withBody);
    }
}