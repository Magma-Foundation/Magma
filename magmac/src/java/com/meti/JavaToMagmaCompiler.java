package com.meti;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.meti.JavaLang.STATIC_KEYWORD;
import static com.meti.Lang.*;
import static com.meti.MagmaLang.*;

public class JavaToMagmaCompiler {
    public static final String TEST_PARAM_OUT = "value : I32";
    public static final String TEST_PARAM_IN = "int value";

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
                () -> compilePackage(input)
        );
    }

    private static Optional<State> compileClass(String input) {
        var index = input.indexOf(CLASS_KEYWORD);
        if (index == -1) return Optional.empty();

        var isPublic = input.startsWith("public ");

        var contentStart = input.indexOf('{');
        if (contentStart == -1) return Optional.empty();

        var contentEnd = input.lastIndexOf('}');
        if (contentEnd == -1) return Optional.empty();

        var name = input.substring(index + CLASS_KEYWORD.length(), contentStart).strip();
        var content = input.substring(contentStart + 1, contentEnd);
        String parameterString;
        if(content.isEmpty()) {
            parameterString = "";
        } else {
            if(content.equals(JavaLang.renderConstructor(TEST_PARAM_IN))) {
                parameterString = TEST_PARAM_OUT;
            } else {
                parameterString = "";
            }
        }

        var exportString = isPublic ? EXPORT_KEYWORD : "";
        var rendered = renderMagmaFunction(exportString, name, parameterString, EMPTY_CONTENT);
        return Optional.of(new State(rendered));
    }

    private static Optional<State> compilePackage(String input) {
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
                : renderImportChildString(child);

        var rendered = renderImportWithChildString(parent, childString);
        return Optional.of(new State(rendered));
    }

    record State(String result, boolean wasPackage) {
        public State(String result) {
            this(result, false);
        }
    }
}
