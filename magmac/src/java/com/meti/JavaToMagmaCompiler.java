package com.meti;

import java.util.Arrays;

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
            var state = runForRootStatement(line);
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

    private static State runForRootStatement(String input) throws CompileException {
        if (input.startsWith(CLASS_KEYWORD)) {
            var name = input.substring(CLASS_KEYWORD.length(), input.indexOf(CONTENT));
            var rendered = MagmaLang.renderMagmaFunction(name);
            return new State(rendered);
        }

        if (input.startsWith(IMPORT_KEYWORD)) {
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
            return new State(rendered);
        }

        if (input.startsWith(JavaLang.PACKAGE_KEYWORD)) {
            return new State("", true);
        }

        throw new CompileException("Unknown input: " + input);
    }

    record State(String result, boolean wasPackage) {
        public State(String result) {
            this(result, false);
        }
    }
}
