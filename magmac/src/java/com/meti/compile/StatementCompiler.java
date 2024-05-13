package com.meti.compile;

import com.meti.result.Err;
import com.meti.result.Ok;
import com.meti.result.Result;

import java.util.Optional;

import static com.meti.compile.ValueCompiler.compileInvocation;

public record StatementCompiler(String input, int indent) {
    private static Optional<Result<String, CompileException>> compileTry(String stripped) throws CompileException {
        if (!stripped.startsWith("try ")) return Optional.empty();

        return Optional.of(new Ok<>("\t\ttry " + compileBlock(stripped, 2)));
    }

    private static Optional<Result<String, CompileException>> compileElse(String stripped) {
        if (!stripped.startsWith("else ")) return Optional.empty();

        try {
            return Optional.of(new Ok<>("\t\ttry " + compileBlock(stripped, 2)));
        } catch (CompileException e) {
            return Optional.of(new Err<>(e));
        }
    }

    private static String compileBlock(String stripped, int indent) throws CompileException {
        var contentStart = stripped.indexOf('{');
        if (contentStart == -1) {
            throw new CompileException("Not a block: " + stripped);
        }

        var contentEnd = stripped.lastIndexOf('}');
        if (contentEnd == -1) {
            throw new CompileException("Not a block: " + stripped);
        }

        var after = stripped.substring(contentStart + 1, contentEnd);

        var inputStatements = Strings.splitMembers(after);
        var output = new StringBuilder();
        for (String inputStatement : inputStatements) {
            if (inputStatement.isBlank()) continue;
            try {
                var compiled = new StatementCompiler(inputStatement, indent + 1).compile();
                output.append(compiled);
            } catch (CompileException e) {
                throw new CompileException("Failed to compile block: " + stripped, e);
            }
        }

        return "{\n" + output + "\t\t}\n";
    }

    String compile() throws CompileException {
        var stripped = input.strip();

        return compileTry(stripped)
                .or(() -> compileCatch(stripped))
                .or(() -> compileThrow(stripped))
                .or(() -> compileFor(stripped))
                .or(() -> compileReturn(stripped))
                .or(() -> compileIf(stripped, indent))
                .or(() -> compileElse(stripped))
                .or(() -> compileAssignment(stripped, indent))
                .or(() -> new DeclarationCompiler(stripped, indent).compile())
                .or(() -> compileInvocation(stripped, 3))
                .or(() -> compileSuffixOperator(stripped))
                .orElseGet(() -> new Err<>(new CompileException("Unknown statement: " + stripped)))
                .mapErr(err -> new CompileException("Failed to compile statement: " + stripped, err)).$();
    }

    private Optional<? extends Result<String, CompileException>> compileSuffixOperator(String stripped) {
        if (!stripped.endsWith("++") && !stripped.endsWith("--")) return Optional.empty();

        try {
            var value = stripped.substring(0, stripped.length() - 2);
            var result = new ValueCompiler(value).compileRequired();
            return Optional.of(new Ok<>(result));
        } catch (CompileException e) {
            return Optional.of(new Err<>(e));
        }
    }

    private Optional<? extends Result<String, CompileException>> compileAssignment(String stripped, int indent) {
        var separator = stripped.indexOf('=');
        if (separator != -1) {
            var left = stripped.substring(0, separator).strip();
            if (!Strings.isAssignable(left)) {
                return Optional.empty();
            }

            var right = stripped.substring(separator + 1).strip();
            try {
                return Optional.of(new Ok<>("\t".repeat(indent) + left + " = " + new ValueCompiler(right).compileRequired() + ";\n"));
            } catch (CompileException e) {
                return Optional.of(new Err<>(e));
            }
        }

        return Optional.empty();
    }

    private Optional<? extends Result<String, CompileException>> compileReturn(String stripped) {
        if (stripped.startsWith("return ")) {
            try {
                var valueString = stripped.substring("return ".length()).strip();
                String outputValueString;
                if (valueString.isEmpty()) {
                    outputValueString = "";
                } else {
                    var compiledValue = new ValueCompiler(valueString).compileRequired();
                    outputValueString = " " + compiledValue;
                }

                return Optional.of(new Ok<>("\t\treturn" + outputValueString + ";\n"));
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
        if (conditionStart == -1) {
            return Optional.of(new Err<>(new CompileException("No starting parentheses in for: " + stripped)));
        }

        var conditionEnd = -1;
        var depth = 0;
        for (int i = conditionStart + 1; i < stripped.length(); i++) {
            var c = stripped.charAt(i);
            if (c == ')' && depth == 0) {
                conditionEnd = i;
                break;
            } else {
                if (c == '(') depth++;
                if (c == ')') depth--;
            }
        }

        if (conditionEnd == -1) {
            return Optional.of(new Err<>(new CompileException("No ending parentheses in for: " + stripped)));
        }

        var condition = stripped.substring(conditionStart + 1, conditionEnd).strip();

        Result<String, CompileException> result;
        try {
            var separator = condition.lastIndexOf(':');
            String compiledBlock;
            String conditionString;
            if (separator == -1) {
                var first = condition.indexOf(';');
                if (first == -1) return Optional.empty();

                var second = condition.indexOf(';', first + 1);
                if (second == -1) return Optional.empty();

                var initialString = condition.substring(0, first).strip();
                var terminatingString = condition.substring(first + 1, second).strip();
                var incrementString = condition.substring(second + 1).strip();

                var initial = new DeclarationCompiler(initialString, 0).compile().orElseThrow(() -> new CompileException("Invalid initial assignment: " + initialString));

                var terminating = new ValueCompiler(terminatingString).compileRequired();
                var increment = new StatementCompiler(incrementString, 0).compile();

                compiledBlock = compileBlock(stripped, indent);
                conditionString = initial + ";" + terminating + increment;
            } else {
                var initialString = condition.substring(0, separator);
                var initial = new DeclarationCompiler(initialString, 0).compile().orElseThrow(() -> new CompileException("Invalid initial assignment: " + initialString));

                var container = condition.substring(separator + 1);

                compiledBlock = compileBlock(stripped, indent);
                conditionString = initial + " : " + container;
            }

            result = new Ok<>("\t".repeat(indent) + "for (" + conditionString + ") " + compiledBlock);
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
            var compiledValue = new ValueCompiler(valueString).compileRequired();
            result = new Ok<>("\t\t\tthrow " + compiledValue + ";\n");
        } catch (CompileException e) {
            result = new Err<>(e);
        }
        return Optional.of(result);
    }

    private Optional<? extends Result<String, CompileException>> compileIf(String stripped, int indent) {
        if (!stripped.startsWith("if")) return Optional.empty();

        var conditionStart = stripped.indexOf('(');
        if (conditionStart == -1) return Optional.empty();

        var conditionEnd = -1;
        var depth = 0;
        for (int i = conditionStart + 1; i < stripped.length(); i++) {
            var c = stripped.charAt(i);
            if (c == ')' && depth == 0) {
                conditionEnd = i;
                break;
            } else {
                if (c == '(') depth++;
                if (c == ')') depth--;
            }
        }

        if (conditionEnd == -1) return Optional.empty();

        Result<String, CompileException> result;
        try {
            var condition = stripped.substring(conditionStart + 1, conditionEnd).strip();
            var compiledCondition = new ValueCompiler(condition).compileRequired();

            String compiledValue;
            if (stripped.contains("{") && stripped.endsWith("}")) {
                compiledValue = compileBlock(stripped, indent);
            } else {
                var valueString = stripped.substring(conditionEnd + 1).strip();
                compiledValue = new StatementCompiler(valueString, 0).compile();
            }
            result = new Ok<>("\t".repeat(indent) + "if (" + compiledCondition + ") " + compiledValue);
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
            var compiledBlock = compileBlock(stripped, 2);
            result = new Ok<>("\t\tcatch (" + catchName + " : " + catchType + ") " + compiledBlock);
        } catch (CompileException e) {
            result = new Err<>(e);
        }

        return Optional.of(result);
    }
}