package com.meti;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.meti.JavaLang.STATIC_KEYWORD;
import static com.meti.Lang.*;

public class JavaToMagmaCompiler {
    static String run(String input) throws CompileException {
        var lines = Arrays.stream(input.split(";"))
                .filter(line -> !line.isEmpty())
                .toList();

        var builder = new StringBuilder();
        var hasSeenPackage = false;
        for (String line : lines) {
            var state = compileRootStatement(line);
            builder.append(state.result);

            if (state.wasPackage) {
                if (hasSeenPackage) {
                    throw new CompileException("Only one package statement is allowed.");
                } else {
                    hasSeenPackage = true;
                }
            }
        }

        return builder.toString();
    }

    private static State compileRootStatement(String input) throws CompileException {
        return streamCompilers(input)
                .map(Supplier::get)
                .flatMap(Optional::stream)
                .findFirst()
                .orElseThrow(() -> new CompileException("Unknown input: " + input));
    }

    private static Stream<Supplier<Optional<State>>> streamCompilers(String input) {
        return Stream.of(
                () -> compileClass(input),
                () -> compileImport(input),
                () -> getState1(input)
        );
    }

    private static Optional<State> compileClass(String input) {
        if (!input.startsWith(CLASS_KEYWORD)) return Optional.empty();

        var name = input.substring(CLASS_KEYWORD.length(), input.indexOf(CONTENT));
        var rendered = MagmaLang.renderMagmaFunction(name);
        return Optional.of(new State(rendered));
    }

    private static Optional<State> getState1(String input) {
        if (!input.startsWith(JavaLang.PACKAGE_KEYWORD)) return Optional.empty();

        return Optional.of(new State("", true));
    }

    private static Optional<State> compileImport(String input) {
        if (!input.startsWith(IMPORT_KEYWORD)) return Optional.empty();

        var isStatic = input.startsWith(IMPORT_KEYWORD + STATIC_KEYWORD);

        var separator = input.lastIndexOf('.');
        var parentStart = isStatic
                ? IMPORT_KEYWORD.length() + STATIC_KEYWORD.length()
                : IMPORT_KEYWORD.length();

        var parent = input.substring(parentStart, separator);

        var child = input.substring(separator + 1).strip();
        var childString = child.equals("*")
                ? "*"
                : MagmaLang.renderImportChildString(child);

        var rendered = MagmaLang.renderImportWithChildString(parent, childString);
        return Optional.of(new State(rendered));
    }

    record State(String result, boolean wasPackage) {
        public State(String result) {
            this(result, false);
        }
    }
}
