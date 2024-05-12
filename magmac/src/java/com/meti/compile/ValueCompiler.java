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
            if(inputArgument.isBlank()) continue;

            try {
                var compiledValue = new ValueCompiler(inputArgument).compile();
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

            return Optional.of(new Ok<>("\t".repeat(indent) + new ValueCompiler(caller).compile() + "(" + renderedArguments + ")" + suffix));
        } catch (CompileException e) {
            return Optional.of(new Err<>(e));
        }
    }

    private static Optional<Result<String, CompileException>> compileSymbol(String stripped) {
        if (Strings.isSymbol(stripped)) {
            return Optional.of(new Ok<>(stripped));
        } else {
            return Optional.empty();
        }
    }

    private static Optional<Result<String, CompileException>> compileAccess(String stripped) {
        var separator = stripped.indexOf('.');
        if (separator == -1) return Optional.empty();

        var objectString = stripped.substring(0, separator);
        var child = stripped.substring(separator + 1);

        String compiledObject;
        try {
            compiledObject = new ValueCompiler(objectString).compile();
        } catch (CompileException e) {
            return Optional.of(new Err<>(new CompileException("Failed to compile object reference: " + objectString, e)));
        }

        return Optional.of(new Ok<>(compiledObject + "." + child));
    }

    private static Optional<Result<String, CompileException>> compileString(String stripped) {
        return stripped.startsWith("\"") && stripped.endsWith("\"")
                ? Optional.of(new Ok<>(stripped))
                : Optional.empty();
    }

    String compile() throws CompileException {
        var stripped = input().strip();

        return compileString(stripped)
                .or(() -> compileAccess(stripped))
                .or(() -> compileSymbol(stripped))
                .or(() -> compileInvocation(stripped, 0))
                .orElseGet(() -> new Err<>(new CompileException("Unknown value: " + stripped)))
                .$();
    }
}