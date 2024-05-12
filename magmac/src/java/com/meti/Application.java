package com.meti;

import com.meti.compile.*;
import com.meti.compile.Compiler;
import com.meti.result.Err;

import java.util.Optional;
import java.util.stream.Stream;

public class Application {

    static String compile(String input) throws CompileException {
        var lines = Strings.splitMembers(input);

        var output = new StringBuilder();
        for (String line : lines) {
            output.append(compileRoot(line));
        }

        return output.toString();
    }

    private static String compileRoot(String line) throws CompileException {
        var stripped = line.strip();

        if (stripped.isEmpty() || stripped.startsWith("package ")) return "";

        return Stream.of(new ImportCompiler(stripped), new ClassCompiler(stripped))
                .map(Compiler::compile)
                .flatMap(Optional::stream)
                .findFirst()
                .orElseGet(() -> new Err<>(new CompileException(line)))
                .$();
    }
}
