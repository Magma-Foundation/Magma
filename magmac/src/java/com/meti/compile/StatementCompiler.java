package com.meti.compile;

import com.meti.result.Err;
import com.meti.result.Ok;
import com.meti.result.Result;

import java.util.Optional;

public record StatementCompiler(String input) {

    private static Optional<Result<String, CompileException>> compileInvocation(String stripped) {
        var start = stripped.indexOf('(');
        if (start == -1) return Optional.empty();

        var end = stripped.lastIndexOf(')');
        if (end == -1) return Optional.empty();

        var caller = stripped.substring(0, start);
        var inputArguments = stripped.substring(start + 1, end).split(",");
        var outputArguments = new StringBuilder();
        for (String inputArgument : inputArguments) {
            try {
                outputArguments.append(compileValue(inputArgument));
            } catch (CompileException e) {
                return Optional.of(new Err<>(e));
            }
        }

        try {
            return Optional.of(new Ok<>(compileValue(caller) + "(" + outputArguments + ")"));
        } catch (CompileException e) {
            return Optional.of(new Err<>(e));
        }
    }

    private static String compileValue(String input) throws CompileException {
        var stripped = input.strip();

        return compileString(stripped)
                .or(() -> compileAccess(stripped))
                .or(() -> compileSymbol(stripped))
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
            compiledParent = compileValue(parent);
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

    private static Optional<Result<String, CompileException>> compileTry(String stripped) throws CompileException {
        if (!stripped.startsWith("try ")) return Optional.empty();

        var contentStart = stripped.indexOf('{');
        var contentEnd = stripped.lastIndexOf('}');
        var after = stripped.substring(contentStart + 1, contentEnd);

        var inputStatements = Strings.splitMembers(after);
        var output = new StringBuilder();
        for (String inputStatement : inputStatements) {
            if (inputStatement.isBlank()) continue;
            output.append(new StatementCompiler(inputStatement).compile());
        }

        return Optional.of(new Ok<>("try {" + output + "}"));
    }

    String compile() throws CompileException {
        var stripped = input().strip();

        return compileTry(stripped)
                .or(() -> compileInvocation(stripped))
                .orElseGet(() -> new Err<>(new CompileException("Unknown statement: " + stripped)))
                .$();
    }
}