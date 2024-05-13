package com.meti.compile;

import com.meti.result.Err;
import com.meti.result.Ok;
import com.meti.result.Result;

import java.util.Optional;

import static com.meti.compile.ValueCompiler.compileInvocation;

public record StatementCompiler(String input) {
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
                .or(() -> new DeclarationCompiler(stripped).compile())
                .or(() -> compileFor(stripped))
                .or(() -> compileReturn(stripped))
                .or(() -> compileInvocation(stripped, 3))
                .orElseGet(() -> new Err<>(new CompileException("Unknown statement: " + stripped)))
                .mapErr(err -> new CompileException("Failed to compile statement: " + stripped, err))
                .$();
    }

    private Optional<? extends Result<String, CompileException>> compileReturn(String stripped) {
        if(stripped.startsWith("return ")) {
            try {
                var valueString = stripped.substring("return ".length()).strip();
                var compiledValue = new ValueCompiler(valueString).compile();
                return Optional.of(new Ok<>("\t\treturn " + compiledValue + ";\n"));
            } catch (CompileException e) {
                return Optional.of(new Err<>(e));
            }
        } else {
            return Optional.empty();
        }
    }

    private Optional<? extends Result<String, CompileException>> compileFor(String stripped) {
        if (!stripped.startsWith("for")) return Optional.empty();

        var conditionStart = stripped.indexOf('(');
        if (conditionStart == -1) return Optional.empty();

        var conditionEnd = stripped.indexOf(')');
        if (conditionEnd == -1) return Optional.empty();

        var condition = stripped.substring(conditionStart + 1, conditionEnd).strip();
        var separator = condition.lastIndexOf(':');

        var conditionDeclarationString = condition.substring(0, separator);
        var compiledConditionDeclaration = new DeclarationCompiler(conditionDeclarationString)
                .compile();
        if(compiledConditionDeclaration.isEmpty()) {
            return Optional.empty();
        }

        var container = condition.substring(separator + 1);

        Result<String, CompileException> result;
        try {
            var compiledBlock = compileBlock(stripped);
            result = new Ok<>("\t\tfor (" + compiledConditionDeclaration.get().$() + " : " + container + ") " + compiledBlock);
        } catch (CompileException e) {
            result = new Err<>(e);
        }

        return Optional.of(result);
    }

    private Optional<? extends Result<String, CompileException>> compileThrow(String stripped) {
        if (!stripped.startsWith("throw ")) return Optional.empty();
        var valueString = stripped.substring("throw ".length());

        Result<String, CompileException> result;
        try {
            var compiledValue = new ValueCompiler(valueString).compile();
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