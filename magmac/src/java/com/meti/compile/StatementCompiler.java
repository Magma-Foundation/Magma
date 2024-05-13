package com.meti.compile;

import com.meti.result.Err;
import com.meti.result.Ok;
import com.meti.result.Result;

import java.util.Optional;

import static com.meti.compile.ValueCompiler.compileInvocation;

public record StatementCompiler(String input, int indent) {
    private static Optional<Result<String, CompileException>> compileTry(String stripped, int indent) throws CompileException {
        if (!stripped.startsWith("try ")) return Optional.empty();

        return Optional.of(new Ok<>("\t".repeat(indent) + "try " + compileBlock(stripped, indent, 0)));
    }

    private static Optional<Result<String, CompileException>> compileElse(String stripped, int indent) {
        if (!stripped.startsWith("else ")) return Optional.empty();

        try {
            String body;
            if (stripped.contains("{") && stripped.endsWith("}")) {
                body = compileBlock(stripped, 2, 0);
            } else {
                var value = stripped.substring("else ".length());
                body = new StatementCompiler(value, 0).compile();
            }
            return Optional.of(new Ok<>("\t".repeat(indent) + "try " + body));
        } catch (CompileException e) {
            return Optional.of(new Err<>(e));
        }
    }

    private static String compileBlock(String stripped, int indent, int blockStart) throws CompileException {
        var contentStart = stripped.indexOf('{', blockStart);
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

        return "{\n" + output + "\t".repeat(indent) + "}\n";
    }

    private static int findIfEnd(String stripped, int paramStart) {
        var conditionEnd = -1;
        var depth = 0;
        var index = paramStart;

        var queue = Strings.toQueue(stripped.substring(paramStart + 1));
        while (!queue.isEmpty()) {
            var c = queue.pop();
            index++;

            if (c == '\'') {
                var escaped = queue.pop();
                index++;

                if (escaped == '\\') {
                    queue.pop();
                    index++;
                }

                queue.pop();
                index++;
                continue;
            }

            if (c == '\"') {
                while (!queue.isEmpty()) {
                    var next = queue.pop();
                    index++;
                    if (next == '\"') {
                        break;
                    }
                }

                continue;
            }

            if (c == ')' && depth == 0) {
                conditionEnd = index;
                break;
            } else {
                if (c == '(') depth++;
                if (c == ')') depth--;
            }
        }

        return conditionEnd;
    }

    String compile() throws CompileException {
        var stripped = input.strip();

        return compileTry(stripped, indent)
                .or(() -> compileCatch(stripped, indent))
                .or(() -> compileThrow(stripped, indent))
                .or(() -> compileFor(stripped, indent))
                .or(() -> compileReturn(stripped, indent))
                .or(() -> compileIf(stripped, indent))
                .or(() -> compileWhile(stripped, indent))
                .or(() -> compileElse(stripped, indent))
                .or(() -> compileAssignment(stripped, indent))
                .or(() -> new DeclarationCompiler(stripped, indent).compile())
                .or(() -> compileInvocation(stripped, indent))
                .or(() -> compileSuffixOperator(stripped))
                .or(() -> compileComment(stripped))
                .orElseGet(() -> new Err<>(new CompileException("Unknown statement: " + stripped)))
                .mapErr(err -> new CompileException("Failed to compile statement: " + stripped, err)).$();
    }

    private Optional<? extends Result<String, CompileException>> compileComment(String stripped) {
        if(stripped.startsWith("/*") && stripped.endsWith("*/")) {
            return Optional.of(new Ok<>(stripped));
        } else {
            return Optional.empty();
        }
    }

    private Optional<? extends Result<String, CompileException>> compileSuffixOperator(String stripped) {
        if (!stripped.endsWith("++") && !stripped.endsWith("--")) return Optional.empty();

        try {
            var value = stripped.substring(0, stripped.length() - 2);
            var result = new ValueCompiler(value, 0).compileRequired();
            return Optional.of(new Ok<>(result));
        } catch (CompileException e) {
            return Optional.of(new Err<>(new CompileException("Failed to compile suffix operator: " + stripped, e)));
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
                return Optional.of(new Ok<>("\t".repeat(indent) + left + " = " + new ValueCompiler(right, 0).compileRequired() + ";\n"));
            } catch (CompileException e) {
                return Optional.of(new Err<>(e));
            }
        }

        return Optional.empty();
    }

    private Optional<? extends Result<String, CompileException>> compileReturn(String stripped, int indent) {
        if (stripped.startsWith("return ")) {
            try {
                var valueString = stripped.substring("return ".length()).strip();
                String outputValueString;
                if (valueString.isEmpty()) {
                    outputValueString = "";
                } else {
                    var compiledValue = new ValueCompiler(valueString, 0).compileRequired();
                    outputValueString = " " + compiledValue;
                }

                return Optional.of(new Ok<>("\t".repeat(indent) + "return" + outputValueString + ";\n"));
            } catch (CompileException e) {
                return Optional.of(new Err<>(new CompileException("Failed to compile return statement: " + stripped, e)));
            }
        } else {
            return Optional.empty();
        }
    }

    private Optional<? extends Result<String, CompileException>> compileFor(String stripped, int indent) {
        if (!stripped.startsWith("for")) return Optional.empty();

        var conditionStart = stripped.indexOf('(');
        if (conditionStart == -1) {
            return Optional.of(new Err<>(new CompileException("No starting parentheses in for: " + stripped)));
        }

        var conditionEnd = findIfEnd(stripped, conditionStart);

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

                var terminating = new ValueCompiler(terminatingString, 0).compileRequired();
                var increment = new StatementCompiler(incrementString, 0).compile();

                compiledBlock = compileBlock(stripped, indent, 0);
                conditionString = initial + ";" + terminating + increment;
            } else {
                var initialString = condition.substring(0, separator);
                var initial = new DeclarationCompiler(initialString, 0)
                        .compile()
                        .orElseThrow(() -> new CompileException("Invalid initial assignment: " + initialString))
                        .$();

                var container = condition.substring(separator + 1);

                compiledBlock = compileBlock(stripped, indent, 0);
                conditionString = initial + " : " + container;
            }

            result = new Ok<>("\t".repeat(this.indent) + "for (" + conditionString + ") " + compiledBlock);
        } catch (CompileException e) {
            result = new Err<>(e);
        }

        return Optional.of(result);
    }

    private Optional<? extends Result<String, CompileException>> compileThrow(String stripped, int indent) {
        if (!stripped.startsWith("throw ")) return Optional.empty();
        var valueString = stripped.substring("throw ".length());

        Result<String, CompileException> result;
        try {
            var compiledValue = new ValueCompiler(valueString, 0).compileRequired();
            result = new Ok<>("\t".repeat(indent) + "throw " + compiledValue + ";\n");
        } catch (CompileException e) {
            result = new Err<>(e);
        }
        return Optional.of(result);
    }

    private Optional<? extends Result<String, CompileException>> compileWhile(String stripped, int indent) {
        if (!stripped.startsWith("while")) return Optional.empty();

        var conditionStart = stripped.indexOf('(');
        if (conditionStart == -1) return Optional.empty();

        var conditionEnd = findIfEnd(stripped, conditionStart);

        if (conditionEnd == -1) return Optional.empty();

        Result<String, CompileException> result;
        try {
            var condition = stripped.substring(conditionStart + 1, conditionEnd).strip();
            var compiledCondition = new ValueCompiler(condition, 0).compileRequired();

            String compiledValue;
            if (stripped.contains("{") && stripped.endsWith("}")) {
                compiledValue = compileBlock(stripped, indent, 0);
            } else {
                var valueString = stripped.substring(conditionEnd + 1).strip();
                compiledValue = new StatementCompiler(valueString, 0).compile();
            }
            result = new Ok<>("\t".repeat(indent) + "while (" + compiledCondition + ") " + compiledValue);
        } catch (CompileException e) {
            result = new Err<>(e);
        }

        return Optional.of(result);
    }

    private Optional<? extends Result<String, CompileException>> compileIf(String stripped, int indent) {
        if (!stripped.startsWith("if")) return Optional.empty();

        var conditionStart = stripped.indexOf('(');
        if (conditionStart == -1) return Optional.empty();

        var conditionEnd = findIfEnd(stripped, conditionStart);
        if (conditionEnd == -1) return Optional.empty();

        Result<String, CompileException> result;
        try {
            var condition = stripped.substring(conditionStart + 1, conditionEnd).strip();
            var compiledCondition = new ValueCompiler(condition, 0).compileRequired();

            String compiledValue;
            if (stripped.contains("{") && stripped.endsWith("}")) {
                compiledValue = compileBlock(stripped, indent, conditionEnd);
            } else {
                var valueString = stripped.substring(conditionEnd + 1).strip();
                compiledValue = new StatementCompiler(valueString, indent).compile();
            }
            result = new Ok<>("\t".repeat(indent) + "if (" + compiledCondition + ") " + compiledValue);
        } catch (CompileException e) {
            result = new Err<>(e);
        }

        return Optional.of(result);
    }

    private Optional<? extends Result<String, CompileException>> compileCatch(String stripped, int indent) {
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
            var compiledBlock = compileBlock(stripped, indent, 0);
            result = new Ok<>("\t".repeat(indent) + "catch (" + catchName + " : " + catchType + ") " + compiledBlock);
        } catch (CompileException e) {
            result = new Err<>(e);
        }

        return Optional.of(result);
    }
}