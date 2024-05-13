package com.meti;

import com.meti.compile.RootCompiler;
import com.meti.compile.*;
import com.meti.result.Err;

import java.util.ArrayList;
import java.util.Collections;
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

        return streamCompilers(stripped)
                .map(rootCompiler -> rootCompiler.compile(new ArrayList<>()))
                .flatMap(Optional::stream)
                .findFirst()
                .orElseGet(() -> new Err<>(new CompileException(line)))
                .$();
    }

    private static Stream<RootCompiler> streamCompilers(String stripped) {
        return Stream.of(
                new ImportCompiler(stripped),
                new ClassCompiler(stripped),
                new RecordCompiler(stripped),
                new InterfaceCompiler(stripped));
    }
}
