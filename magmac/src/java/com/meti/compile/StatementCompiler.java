package com.meti.compile;

import com.meti.api.result.Err;
import com.meti.api.result.Ok;
import com.meti.api.result.Result;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.meti.compile.InvocationCompiler.compileInvocation;
import static com.meti.api.result.Results.$Result;

public record StatementCompiler(String input, int indent) {
    private static Optional<Result<String, CompileException>> compileTry(String stripped, int indent) throws CompileException {
        if (!stripped.startsWith("try ")) return Optional.empty();

        return Optional.of(new Ok<>("\t".repeat(indent) + "try " + compileBlock(stripped, indent, 0, Collections.emptyList())));
    }

    private static Optional<Result<String, CompileException>> compileElse(String stripped, int indent) {
        if (!stripped.startsWith("else ")) return Optional.empty();

        try {
            String body;
            if (stripped.contains("{") && stripped.endsWith("}")) {
                body = compileBlock(stripped, 2, 0, Collections.emptyList());
            } else {
                var value = stripped.substring("else ".length());
                final StatementCompiler statementCompiler = new StatementCompiler(value, 0);
                body = StatementCompiler.compileUnsafe(Collections.emptyList(), statementCompiler.input, statementCompiler.indent);
            }
            return Optional.of(new Ok<>("\t".repeat(indent) + "try " + body));
        } catch (CompileException e) {
            return Optional.of(new Err<>(e));
        }
    }

    private static String compileBlock(String stripped, int indent, int blockStart, List<String> stack) throws CompileException {
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
                final StatementCompiler statementCompiler = new StatementCompiler(inputStatement, indent + 1);
                var compiled = StatementCompiler.compileUnsafe(stack, statementCompiler.input, statementCompiler.indent);
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

    static String compileUnsafe(List<String> stack, String input, int indent) throws CompileException {
        var stripped = input.strip();

        return compileTry(stripped, indent)
                .or(() -> StatementCompiler.compileCatch(stripped, indent))
                .or(() -> StatementCompiler.compileThrow(stripped, indent))
                .or(() -> StatementCompiler.compileFor(stripped, indent, stack))
                .or(() -> StatementCompiler.compileReturn(stripped, indent))
                .or(() -> StatementCompiler.compileIf(stripped, indent))
                .or(() -> StatementCompiler.compileWhile(stripped, indent))
                .or(() -> compileElse(stripped, indent))
                .or(() -> StatementCompiler.compileAssignment(stripped, indent))
                .or(() -> DeclarationCompiler.compile(stack, stripped, indent))
                .or(() -> compileInvocation(stack, stripped, indent))
                .or(() -> StatementCompiler.compileSuffixOperator(stripped))
                .or(() -> StatementCompiler.compileComment(stripped))
                .orElseGet(() -> new Err<>(new CompileException("Unknown statement: " + stripped)))
                .mapErr(err -> {
                    var format = "Failed to compile statement - %s: %s";
                    var message = format.formatted(stack, stripped);
                    return new CompileException(message, err);
                }).$();
    }

    private static Optional<? extends Result<String, CompileException>> compileComment(String stripped) {
        if (stripped.startsWith("/*") && stripped.endsWith("*/")) {
            return Optional.of(new Ok<>(stripped));
        } else {
            return Optional.empty();
        }
    }

    private static Optional<? extends Result<String, CompileException>> compileSuffixOperator(String stripped) {
        if (!stripped.endsWith("++") && !stripped.endsWith("--")) return Optional.empty();

        try {
            var value = stripped.substring(0, stripped.length() - 2);
            var result = ValueCompiler.compileRequired(ValueCompiler.createValueCompiler(value, 0), Collections.emptyList());
            return Optional.of(new Ok<>(result));
        } catch (CompileException e) {
            return Optional.of(new Err<>(new CompileException("Failed to compile suffix operator: " + stripped, e)));
        }
    }

    private static Optional<? extends Result<String, CompileException>> compileAssignment(String stripped, int indent) {
        var separator = stripped.indexOf('=');
        if (separator != -1) {
            var left = stripped.substring(0, separator).strip();
            if (!Strings.isAssignable(left)) {
                return Optional.empty();
            }

            var right = stripped.substring(separator + 1).strip();
            try {
                return Optional.of(new Ok<>("\t".repeat(indent) + left + " = " + ValueCompiler.compileRequired(ValueCompiler.createValueCompiler(right, 0), Collections.emptyList()) + ";\n"));
            } catch (CompileException e) {
                return Optional.of(new Err<>(e));
            }
        }

        return Optional.empty();
    }

    private static Optional<? extends Result<String, CompileException>> compileReturn(String stripped, int indent) {
        if (stripped.startsWith("return ")) {
            try {
                var valueString = stripped.substring("return ".length()).strip();
                String outputValueString;
                if (valueString.isEmpty()) {
                    outputValueString = "";
                } else {
                    var compiledValue = ValueCompiler.compileRequired(ValueCompiler.createValueCompiler(valueString, 0), Collections.emptyList());
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

    private static Optional<? extends Result<String, CompileException>> compileFor(String stripped, int indent, List<String> stack) {
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

                var initial = DeclarationCompiler
                        .compile(stack, initialString, 0)
                        .orElseThrow(() -> new CompileException("Invalid initial assignment: " + initialString));

                var terminating = ValueCompiler.compileRequired(ValueCompiler.createValueCompiler(terminatingString, 0), stack);
                final StatementCompiler statementCompiler1 = new StatementCompiler(incrementString, 0);
                var increment = StatementCompiler.compileUnsafe(Collections.emptyList(), statementCompiler1.input, statementCompiler1.indent);

                compiledBlock = compileBlock(stripped, indent, 0, Collections.emptyList());
                conditionString = initial + ";" + terminating + increment;
            } else {
                var initialString = condition.substring(0, separator);
                var initial = DeclarationCompiler
                        .compile(stack, initialString, 0)
                        .orElseThrow(() -> new CompileException("Invalid initial assignment: " + initialString))
                        .$();

                var container = condition.substring(separator + 1);

                compiledBlock = compileBlock(stripped, indent, 0, stack);
                conditionString = initial + " : " + container;
            }

            result = new Ok<>("\t".repeat(indent) + "for (" + conditionString + ") " + compiledBlock);
        } catch (CompileException e) {
            var format = "Failed to compile for-loop - %s: %s";
            var message = format.formatted(stack, stripped);
            result = new Err<>(new CompileException(message, e));
        }

        return Optional.of(result);
    }

    private static Optional<? extends Result<String, CompileException>> compileThrow(String stripped, int indent) {
        if (!stripped.startsWith("throw ")) return Optional.empty();
        var valueString = stripped.substring("throw ".length());

        Result<String, CompileException> result;
        try {
            var compiledValue = ValueCompiler.compileRequired(ValueCompiler.createValueCompiler(valueString, 0), Collections.emptyList());
            result = new Ok<>("\t".repeat(indent) + "throw " + compiledValue + ";\n");
        } catch (CompileException e) {
            result = new Err<>(e);
        }
        return Optional.of(result);
    }

    private static Optional<? extends Result<String, CompileException>> compileWhile(String stripped, int indent) {
        if (!stripped.startsWith("while")) return Optional.empty();

        var conditionStart = stripped.indexOf('(');
        if (conditionStart == -1) return Optional.empty();

        var conditionEnd = findIfEnd(stripped, conditionStart);

        if (conditionEnd == -1) return Optional.empty();

        Result<String, CompileException> result;
        try {
            var condition = stripped.substring(conditionStart + 1, conditionEnd).strip();
            var compiledCondition = ValueCompiler.compileRequired(ValueCompiler.createValueCompiler(condition, 0), Collections.emptyList());

            String compiledValue;
            if (stripped.contains("{") && stripped.endsWith("}")) {
                compiledValue = compileBlock(stripped, indent, 0, Collections.emptyList());
            } else {
                var valueString = stripped.substring(conditionEnd + 1).strip();
                final StatementCompiler statementCompiler = new StatementCompiler(valueString, 0);
                compiledValue = StatementCompiler.compileUnsafe(Collections.emptyList(), statementCompiler.input, statementCompiler.indent);
            }
            result = new Ok<>("\t".repeat(indent) + "while (" + compiledCondition + ") " + compiledValue);
        } catch (CompileException e) {
            result = new Err<>(e);
        }

        return Optional.of(result);
    }

    private static Optional<? extends Result<String, CompileException>> compileIf(String stripped, int indent) {
        if (!stripped.startsWith("if")) return Optional.empty();

        var conditionStart = stripped.indexOf('(');
        if (conditionStart == -1) return Optional.empty();

        var conditionEnd = findIfEnd(stripped, conditionStart);
        if (conditionEnd == -1) return Optional.empty();

        Result<String, CompileException> result;
        try {
            var condition = stripped.substring(conditionStart + 1, conditionEnd).strip();
            var compiledCondition = ValueCompiler.compileRequired(ValueCompiler.createValueCompiler(condition, 0), Collections.emptyList());

            String compiledValue;
            if (stripped.contains("{") && stripped.endsWith("}")) {
                compiledValue = compileBlock(stripped, indent, conditionEnd, Collections.emptyList());
            } else {
                var valueString = stripped.substring(conditionEnd + 1).strip();
                final StatementCompiler statementCompiler = new StatementCompiler(valueString, indent);
                compiledValue = StatementCompiler.compileUnsafe(Collections.emptyList(), statementCompiler.input, statementCompiler.indent);
            }
            result = new Ok<>("\t".repeat(indent) + "if (" + compiledCondition + ") " + compiledValue);
        } catch (CompileException e) {
            result = new Err<>(e);
        }

        return Optional.of(result);
    }

    private static Optional<? extends Result<String, CompileException>> compileCatch(String stripped, int indent) {
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
            var compiledBlock = compileBlock(stripped, indent, 0, Collections.emptyList());
            result = new Ok<>("\t".repeat(indent) + "catch (" + catchName + " : " + catchType + ") " + compiledBlock);
        } catch (CompileException e) {
            result = new Err<>(e);
        }

        return Optional.of(result);
    }

    static Result<String, CompileException> compile(int indent, List<String> stack, String inputMember) {
       return $Result(() -> compileUnsafe(stack, inputMember, indent));
   }
}