package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
            output.append(compileRoot(line, sourceExtension, targetExtension));
        }

        return output.toString();
    }

    static String compileRoot(String line, String sourceExtension, String targetExtension) throws CompileException {
        if (sourceExtension.equals("java")) {
            return JavaCompiler.compileJavaRoot(line);
        } else {
            return MagmaCompiler.compileMagmaRoot(line, targetExtension);
        }
    }
}