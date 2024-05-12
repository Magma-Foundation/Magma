package com.meti.compile;

import com.meti.result.Err;
import com.meti.result.Ok;
import com.meti.result.Result;

import java.util.Optional;

public record ValueCompiler(String input) {
    static Optional<Result<String, CompileException>> compileInvocation(String stripped, int indent) {

        var start = stripped.indexOf('(');
        if (start == -1) return Optional.empty();

        var end = stripped.lastIndexOf(')');
        if (end == -1) return Optional.empty();

        var callerStart = stripped.startsWith("new ") ? "new ".length() : 0;
        var caller = stripped.substring(callerStart, start);
        var inputArguments = stripped.substring(start + 1, end).split(",");
        var outputArguments = Optional.<StringBuilder>empty();
        for (String inputArgument : inputArguments) {
            try {
                var compiledValue = compileValue(new ValueCompiler(inputArgument));
                outputArguments = Optional.of(outputArguments
                        .map(inner -> inner.append(", ").append(compiledValue))
                        .orElse(new StringBuilder(compiledValue)));

            } catch (CompileException e) {
                return Optional.of(new Err<>(e));
            }
        }

        try {
            var suffix = indent == 0 ? "" : ";\n";
            var renderedArguments = outputArguments.orElse(new StringBuilder());

            return Optional.of(new Ok<>("\t".repeat(indent) + compileValue(new ValueCompiler(caller)) + "(" + renderedArguments + ")" + suffix));
        } catch (CompileException e) {
            return Optional.of(new Err<>(e));
        }
    }

    static String compileValue(ValueCompiler valueCompiler) throws CompileException {
        var stripped = valueCompiler.input().strip();

        return compileString(stripped)
                .or(() -> compileAccess(stripped))
                .or(() -> compileSymbol(stripped))
                .or(() -> compileInvocation(stripped, 0))
                .orElseGet(() -> new Err<>(new CompileException("Unknown value: " + stripped)))
                .$();
    }

    private static Optional<Result<String, CompileException>> compileSymbol(String stripped) {
        if (stripped.isEmpty()) return Optional.empty();

        var first = stripped.charAt(0);
        if (!Character.isLetter(first)) return Optional.empty();

        for (int i = 1; i < stripped.length(); i++) {
            var c = stripped.charAt(i);
            if (!Character.isLetter(c) && !Character.isDigit(c)) {
                return Optional.empty();
            }
        }

        return Optional.of(new Ok<>(stripped));

    }

    private static Optional<Result<String, CompileException>> compileAccess(String stripped) {
        var separator = stripped.indexOf('.');
        if (separator == -1) return Optional.empty();

        var parent = stripped.substring(0, separator);
        var child = stripped.substring(separator + 1);

        String compiledParent;
        try {
            compiledParent = compileValue(new ValueCompiler(parent));
        } catch (CompileException e) {
            return Optional.of(new Err<>(e));
        }

        return Optional.of(new Ok<>(compiledParent + "." + child));
    }

    private static Optional<Result<String, CompileException>> compileString(String stripped) {
        return stripped.startsWith("\"") && stripped.endsWith("\"")
                ? Optional.of(new Ok<>(stripped))
                : Optional.empty();
    }
}