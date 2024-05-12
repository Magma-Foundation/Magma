package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Collectors;

public class Compiler {
    static void compile(String sourceExtension, String targetExtension) throws IOException, CompileException {
        var source = Paths.get(".", "magmac", "src", "java", "com", "meti", "Main." + sourceExtension);
        var input = Files.readString(source);

        var compileImpl = compile(input, sourceExtension, targetExtension);

        Files.writeString(source.resolveSibling("Main." + targetExtension), compileImpl);
    }

    static String compile(String input, String sourceExtension, String targetExtension) throws CompileException {
        var lines = Splitter.split(input);
        var output = new StringBuilder();
        for (var line : lines) {
            var result = compileRoot(line, sourceExtension, targetExtension);
            output.append(result.findOuter().orElse(""))
                    .append(result.findInner().map(list -> list.stream()
                            .map(Node::findValue)
                            .flatMap(Optional::stream)
                            .collect(Collectors.joining())).orElse(""));
        }

        return output.toString();
    }

    static StackResult compileRoot(String line, String sourceExtension, String targetExtension) throws CompileException {
        if (sourceExtension.equals("java")) {
            return new InnerResult(JavaCompiler.compileJavaRoot(line));
        } else {
            return MagmaCompiler.compileMagmaRoot(line, targetExtension);
        }
    }
}