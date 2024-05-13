package com.meti.compile;

import com.meti.result.Err;
import com.meti.result.Ok;
import com.meti.result.Result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Stream;

import static com.meti.result.Results.$Result;

public record ValueCompiler(String input) {
    static Optional<Result<String, CompileException>> compileInvocation(String stripped, int indent) {
        if (!stripped.endsWith(")")) return Optional.empty();
        var start = findInvocationStart(stripped);
        if (start == -1) return Optional.empty();

        var end = stripped.length() - 1;

        var callerEnd = computeCallerEnd(stripped, start);
        if (callerEnd.isEmpty()) return Optional.empty();

        String caller;
        if (stripped.startsWith("new ")) {
            var temp = stripped.substring("new ".length(), callerEnd.getAsInt());
            if (Strings.isSymbol(temp)) {
                caller = temp;
            } else {
                caller = stripped.substring(0, callerEnd.getAsInt());
            }
        } else {
            caller = stripped.substring(0, callerEnd.getAsInt());
        }


        var inputArgumentStrings = stripped.substring(start + 1, end);

        var inputArguments = splitInvocationArguments(inputArgumentStrings);
        var outputArguments = Optional.<StringBuilder>empty();
        for (String inputArgument : inputArguments) {
            if (inputArgument.isBlank()) continue;

            try {
                var compiledValue = new ValueCompiler(inputArgument).compile();
                if (compiledValue.isEmpty()) {
                    throw new CompileException("Failed to compile argument: " + inputArgument);
                }

                outputArguments = Optional.of(outputArguments.map(inner -> inner.append(", ").append(compiledValue)).orElse(new StringBuilder(compiledValue.get().$())));
            } catch (CompileException e) {
                return Optional.of(new Err<>(new CompileException("Failed to compile invocation: " + stripped, e)));
            }
        }


        var suffix = indent == 0 ? "" : ";\n";
        var renderedArguments = outputArguments.orElse(new StringBuilder());

        var compiledCaller = new ValueCompiler(caller).compile();
        if (compiledCaller.isEmpty()) {
            return Optional.empty();
        }

        try {
            var stringCompileExceptionResult = compiledCaller.get().$();
            return Optional.of(new Ok<>("\t".repeat(indent) + stringCompileExceptionResult + "(" + renderedArguments + ")" + suffix));
        } catch (CompileException e) {
            return Optional.of(new Err<>(new CompileException("Failed to compile invocation: " + stripped, e)));
        }
    }

    private static OptionalInt computeCallerEnd(String stripped, int start) {
        int callerEnd;
        if (stripped.charAt(start - 1) == '>') {
            var genStart = stripped.lastIndexOf("<", start);
            if (genStart == -1) return OptionalInt.empty();
            else callerEnd = genStart;
        } else {
            callerEnd = start;
        }
        return OptionalInt.of(callerEnd);
    }

    private static ArrayList<String> splitInvocationArguments(String inputArgumentStrings) {
        var inputArguments = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;
        var queue = Strings.toQueue(inputArgumentStrings);

        while (!queue.isEmpty()) {
            var c = queue.pop();
            if(c == '\'') {
                builder.append(c);
                builder.append(queue.pop());
                builder.append(queue.pop());
                continue;
            }

            if (c == ',' && depth == 0) {
                inputArguments.add(builder.toString());
                builder = new StringBuilder();
            } else {
                if (c == '(') depth++;
                if (c == ')') depth--;
                builder.append(c);
            }
        }

        inputArguments.add(builder.toString());
        return inputArguments;
    }

    private static int findInvocationStart(String stripped) {
        var queue = Strings.toQueue(stripped);
        Collections.reverse(queue);
        queue.pop();

        var start = -1;
        var depth = 0;
        var index = stripped.length() - 1;

        while (!queue.isEmpty()) {
            var c = queue.pop();
            index--;

            if (c == '\'') {
                queue.pop();
                queue.pop();
                index--;
                index--;
            }

            if (c == '(' && depth == 0) {
                start = index;
                break;
            } else {
                if (c == ')') depth++;
                if (c == '(') depth--;
            }
        }

        return start;
    }

    private static Optional<Result<String, CompileException>> compileSymbol(String stripped) {
        if (Strings.isSymbol(stripped)) {
            return Optional.of(new Ok<>(stripped));
        } else {
            return Optional.empty();
        }
    }

    private static Optional<Result<String, CompileException>> compileAccess(String stripped) {
        var objectEnd = stripped.lastIndexOf('.');
        if (objectEnd == -1) return Optional.empty();

        if (objectEnd == stripped.length() - 1) return Optional.empty();

        var objectString = stripped.substring(0, objectEnd);

        int childStart;
        if (stripped.charAt(objectEnd + 1) != '<') childStart = objectEnd + 1;
        else {
            var newChildStart = stripped.indexOf('>');
            if (newChildStart == -1) {
                return Optional.empty();
            } else {
                childStart = newChildStart + 1;
            }
        }

        var child = stripped.substring(childStart);

        if (!Strings.isAssignable(child)) {
            return Optional.empty();
        }

        String compiledObject;
        try {
            compiledObject = new ValueCompiler(objectString).compileRequired();
        } catch (CompileException e) {
            return Optional.of(new Err<>(new CompileException("Failed to compile object reference of access statement: " + objectString, e)));
        }

        return Optional.of(new Ok<>(compiledObject + "." + child));
    }

    private static Optional<Result<String, CompileException>> compileString(String stripped) {
        return stripped.startsWith("\"") && stripped.endsWith("\"") ? Optional.of(new Ok<>(stripped)) : Optional.empty();
    }

    private static Optional<Result<String, CompileException>> compileOperation(String stripped, String operator) {
        var operatorIndex = stripped.indexOf(operator);
        if (operatorIndex == -1) return Optional.empty();

        var left = stripped.substring(0, operatorIndex).strip();
        var right = stripped.substring(operatorIndex + operator.length());

        return Optional.of($Result(() -> {
            var leftCompiled = new ValueCompiler(left).compileRequired();
            var rightCompiled = new ValueCompiler(right).compileRequired();

            return leftCompiled + " " + operator + " " + rightCompiled;
        }).mapErr(err -> new CompileException("Failed to compile operation '" + operator + "': " + stripped, err)));
    }

    String compileRequired() throws CompileException {
        return compile().orElseGet(() -> new Err<>(new CompileException("Unknown value: " + input))).$();
    }

    Optional<Result<String, CompileException>> compile() {
        var stripped = input().strip();
        return compileString(stripped)
                .or(() -> compileSymbol(stripped))
                .or(() -> compileLambda(stripped))
                .or(() -> compileInvocation(stripped, 0))
                .or(() -> compileAccess(stripped))
                .or(() -> compileTernary(stripped))
                .or(() -> compileNumbers(stripped))
                .or(() -> compileOperation(stripped))
                .or(() -> compileChar(stripped))
                .or(() -> compileNot(stripped))
                .or(() -> compileMethodReference(stripped));
    }

    private Optional<Result<String, CompileException>> compileMethodReference(String stripped) {
        var index = stripped.indexOf("::");
        if (index != -1) {
            var before = stripped.substring(0, index);
            var after = stripped.substring(index + "::".length());
            if (!Strings.isSymbol(after)) return Optional.empty();

            return new ValueCompiler(before).compile().map(value -> {
                return value.mapValue(inner -> {
                    return inner + "." + after;
                });
            });
        }

        return Optional.empty();
    }

    private Optional<? extends Result<String, CompileException>> compileLambda(String stripped) {
        var separator = stripped.indexOf("->");
        if (separator == -1) return Optional.empty();

        var before = stripped.substring(0, separator).strip();
        var paramStart = before.indexOf('(');
        var paramEnd = before.lastIndexOf(')');

        var params = new ArrayList<String>();
        if (paramStart == 0 && paramEnd == before.length() - 1) {
            /*
            Pull params
             */
        } else if (paramStart == -1 && paramEnd == -1) {
            if (Strings.isSymbol(before)) {
                params.add(before);
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }

        var value = stripped.substring(separator + "->".length()).strip();
        try {
            String compiledValue;
            if (value.startsWith("{") && value.endsWith("}")) {
                var inputContent = value.substring(1, value.length() - 1).strip();
                var members = Strings.splitMembers(inputContent);
                compiledValue = MethodCompiler.compileMethodMembers(members).$();
            } else {
                compiledValue = new ValueCompiler(value).compileRequired();
            }
            var rendered = MagmaLang.renderFunction(0, "", "", "", "", " => " + compiledValue);
            return Optional.of(new Ok<>(rendered));
        } catch (CompileException e) {
            return Optional.of(new Err<>(new CompileException("Failed to compile lambda: " + stripped, e)));
        }
    }

    private Optional<? extends Result<String, CompileException>> compileNot(String stripped) {
        if (stripped.startsWith("!")) {
            var valueString = stripped.substring(1).strip();
            try {
                return Optional.of(new Ok<>(new ValueCompiler(valueString).compileRequired()));
            } catch (CompileException e) {
                return Optional.of(new Err<>(e));
            }
        } else {
            return Optional.empty();
        }
    }

    private Optional<? extends Result<String, CompileException>> compileChar(String stripped) {
        if (stripped.startsWith("'") && stripped.endsWith("'")) {
            return Optional.of(new Ok<>(stripped));
        } else {
            return Optional.empty();
        }
    }

    private Optional<? extends Result<String, CompileException>> compileNumbers(String stripped) {
        try {
            Integer.parseInt(stripped);
            return Optional.of(new Ok<>(stripped));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private Optional<? extends Result<String, CompileException>> compileOperation(String stripped) {
        return Stream.of("&&", "==", "!=", "+", "||", "<", "-")
                .map(operator -> compileOperation(stripped, operator))
                .flatMap(Optional::stream)
                .findFirst();
    }

    private Optional<? extends Result<String, CompileException>> compileTernary(String stripped) {
        var conditionMarker = stripped.indexOf('?');
        if (conditionMarker == -1) return Optional.empty();

        var statementMarker = stripped.indexOf(":", conditionMarker);
        if (statementMarker == -1) return Optional.empty();

        var conditionString = stripped.substring(0, conditionMarker).strip();
        var condition = new ValueCompiler(conditionString).compile();
        if (condition.isEmpty()) return Optional.empty();

        var thenString = stripped.substring(conditionMarker + 1, statementMarker).strip();
        var thenBlock = new ValueCompiler(thenString).compile();
        if (thenBlock.isEmpty()) return Optional.empty();

        var elseString = stripped.substring(statementMarker + 1).strip();
        var elseBlock = new ValueCompiler(elseString).compile();
        if (elseBlock.isEmpty()) return Optional.empty();

        var rendered = condition.get() + " ? " + thenBlock.get() + " : " + elseBlock.get();
        return Optional.of(new Ok<>(rendered));
    }
}