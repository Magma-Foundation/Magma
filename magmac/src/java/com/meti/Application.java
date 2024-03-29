package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Application {
    static void run(SourceSet sourceSet, Path targetDirectory, String targetExtension) throws IOException, CompileException {
        var set = sourceSet.collect();

        for (var source : set) {
            var namespace = source.findNamespace();

            var output = compile(source, namespace);

            var without = source.findName();

            var parent = namespace.stream().reduce(targetDirectory, Path::resolve, (path, path2) -> path2);
            if (!Files.exists(parent)) Files.createDirectories(parent);

            var target = parent.resolve(without + targetExtension);
            Files.writeString(target, output);
        }
    }

    private static String compile(PathSource source, List<String> namespace) throws IOException, CompileException {
        var input = source.read();
        String output;
        if (input.isEmpty()) {
            output = "";
        } else if (input.startsWith("package ")) {
            var segments = input.substring("package ".length(), input.lastIndexOf(';'))
                    .strip()
                    .split("\\.");

            var expectedNamespace = Arrays.asList(segments);
            if (namespace.equals(expectedNamespace)) {
                output = "";
            } else {
                var format = "Expected a namespace of '%s' but was actually '%s'.";
                var message = format.formatted(expectedNamespace, namespace);
                throw new CompileException(message);
            }
        } else {
            var format = "Unknown content: '%s'.";
            var message = format.formatted(input);
            throw new CompileException(message);
        }
        return output;
    }
}