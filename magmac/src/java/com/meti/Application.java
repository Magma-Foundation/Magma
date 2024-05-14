package com.meti;

import com.meti.compile.ClassCompiler;
import com.meti.compile.CompileException;
import com.meti.compile.ImportCompiler;
import com.meti.compile.RecordCompiler;
import com.meti.compile.RootCompiler;
import com.meti.compile.Strings;
import com.meti.api.result.Err;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Application {

    static String compile(String input) throws CompileException {
        var lines = Strings.splitMembers(input);

        var output = new StringBuilder();
        var stack = new ArrayList<String>();
        for (String line : lines) {
            output.append(compileRoot(line, stack));
        }

        return output.toString();
    }

    private static String compileRoot(String line, List<String> stack) throws CompileException {
        var stripped = line.strip();

        if (stripped.isEmpty() || stripped.startsWith("package ")) return "";

        return streamCompilers(stripped)
                .map(rootCompiler -> rootCompiler.compile(stack))
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
