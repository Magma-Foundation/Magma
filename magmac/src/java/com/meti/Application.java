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

            var output = compile(source.read(), namespace);

            var without = source.findName();

            var parent = namespace.stream().reduce(targetDirectory, Path::resolve, (path, path2) -> path2);
            if (!Files.exists(parent)) Files.createDirectories(parent);

            var target = parent.resolve(without + targetExtension);
            Files.writeString(target, output);
        }
    }

    static String compile(String input, List<String> namespace) throws CompileException {
        var segments = input.split(";");
        var hasPackage = false;
        for (String segment : segments) {
            var aPackage = isPackage(segment, namespace);
            if (aPackage) {
                if (hasPackage) {
                    throw new CompileException("Input has too many packages!");
                } else {
                    hasPackage = true;
                }
            }
        }

        return "";
    }

    private static boolean isPackage(String input, List<String> namespace) throws CompileException {
        if (input.isEmpty()) {
            return false;
        } else if (input.startsWith("package ")) {
            return compilePackage(input, namespace);
        } else {
            var format = "Unknown content: '%s'.";
            var message = format.formatted(input);
            throw new CompileException(message);
        }
    }

    private static boolean compilePackage(String input, List<String> namespace) throws CompileException {
        var segments = input.substring("package ".length())
                .strip()
                .split("\\.");

        var expectedNamespace = Arrays.asList(segments);
        if (namespace.equals(expectedNamespace)) {
            return true;
        } else {
            var format = "Expected a namespace of '%s' but was actually '%s'.";
            var message = format.formatted(expectedNamespace, namespace);
            throw new CompileException(message);
        }
    }
}