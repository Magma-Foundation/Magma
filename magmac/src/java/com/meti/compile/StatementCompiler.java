package com.meti.compile;

import com.meti.result.Err;
import com.meti.result.Ok;
import com.meti.result.Result;

import java.util.Optional;

public record StatementCompiler(String input) {

    private static Optional<Result<String, CompileException>> compileInvocation(String stripped, int indent) {

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
                var compiledValue = compileValue(inputArgument);
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

            return Optional.of(new Ok<>("\t".repeat(indent) + compileValue(caller) + "(" + renderedArguments + ")" + suffix));
        } catch (CompileException e) {
            return Optional.of(new Err<>(e));
        }
    }

    private static String compileValue(String input) throws CompileException {
        var stripped = input.strip();

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

        return Optional.of(new Ok<>("\t\ttry " + compileBlock(stripped)));
    }

    private static String compileBlock(String stripped) throws CompileException {
        var contentStart = stripped.indexOf('{');
        var contentEnd = stripped.lastIndexOf('}');
        var after = stripped.substring(contentStart + 1, contentEnd);

        var inputStatements = Strings.splitMembers(after);
        var output = new StringBuilder();
        for (String inputStatement : inputStatements) {
            if (inputStatement.isBlank()) continue;
            var compiled = new StatementCompiler(inputStatement).compile();
            output.append(compiled);
        }

        return "{\n" + output + "\t\t}\n";
    }

    String compile() throws CompileException {
        var stripped = input.strip();

        return compileTry(stripped)
                .or(() -> compileCatch(stripped))
                .or(() -> compileThrow(stripped))
                .or(() -> compileInvocation(stripped, 3))
                .orElseGet(() -> new Err<>(new CompileException("Unknown statement: " + stripped)))
                .mapErr(err -> new CompileException("Failed to compile statement: " + stripped, err))
                .$();
    }

    private Optional<? extends Result<String, CompileException>> compileThrow(String stripped) {
        if (!stripped.startsWith("throw ")) return Optional.empty();
        var valueString = stripped.substring("throw ".length());

        Result<String, CompileException> result;
        try {
            var compiledValue = compileValue(valueString);
            result = new Ok<>("\t\t\tthrow " + compiledValue + ";\n");
        } catch (CompileException e) {
            result = new Err<>(e);
        }
        return Optional.of(result);
    }

    private Optional<? extends Result<String, CompileException>> compileCatch(String stripped) {
        if (!stripped.startsWith("catch")) return Optional.empty();

        var typeStart = stripped.indexOf('(');
        if (typeStart == -1) return Optional.empty();

        var typeEnd = stripped.indexOf(')');
        if (typeEnd == -1) return Optional.empty();

        var type = stripped.substring(typeStart + 1, typeEnd).strip();
        var separator = type.lastIndexOf(' ');
        var catchType = type.substring(0, separator);
        var catchName = type.substring(separator + 1);

        Result<String, CompileException> result;
        try {
            var compiledBlock = compileBlock(stripped);
            result = new Ok<>("\t\tcatch (" + catchName + " : " + catchType + ") " + compiledBlock);
        } catch (CompileException e) {
            result = new Err<>(e);
        }

        return Optional.of(result);
    }
}