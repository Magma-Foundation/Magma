package com.meti;

import com.meti.api.collect.JavaString;
import com.meti.compile.Compiler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
            var output = new Compiler(new JavaString(input.strip())).compile();

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
            output.match(outputValue -> {
                try {
                    Files.writeString(outputFile, outputValue.value());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, Throwable::printStackTrace);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}