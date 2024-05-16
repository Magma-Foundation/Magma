package com.meti.compile;

import com.meti.api.result.Err;
import com.meti.api.result.Ok;
import com.meti.api.result.Result;

import java.util.*;

public class InvocationCompiler {
    static Optional<Result<String, CompileException>> compileInvocation(List<String> stack, String stripped, int indent) {
        if (!stripped.endsWith(")")) return Optional.empty();
        var start = findInvocationStart(stripped);
        if (start == -1) return Optional.empty();

        var end = stripped.length() - 1;
        var callerEnd = computeCallerEnd(stripped, start);
        if (callerEnd.isEmpty()) return Optional.empty();

        var caller = computeCaller(stripped, callerEnd);

        var inputArgumentStrings = stripped.substring(start + 1, end);

        var argStrings = splitInvocationArguments(inputArgumentStrings);
        var outputArguments = Optional.<StringBuilder>empty();
        try {
            for (var argString : argStrings) {
                if (argString.isBlank()) continue;
                var compiledArg = compileArgument(stack, argString, indent);

                var value = outputArguments
                        .map(inner -> inner.append(", ").append(compiledArg))
                        .orElse(new StringBuilder(compiledArg));

                outputArguments = Optional.of(value);
            }
        } catch (CompileException e) {
            var format = "Failed to compile invocation - %s: %s";
            var message = format.formatted(stack, stripped);
            return Optional.of(new Err<>(new CompileException(message, e)));
        }

        var suffix = indent == 0 ? "" : ";\n";
        var renderedArguments = outputArguments.orElse(new StringBuilder());

        var compiledCaller = ValueCompiler.compile(ValueCompiler.createValueCompiler(caller, 0), stack, caller, 0);
        if (compiledCaller.isEmpty()) {
            return Optional.empty();
        }

        try {
            var stringCompileExceptionResult = compiledCaller.get().$();
            return Optional.of(new Ok<>("\t".repeat(indent) + stringCompileExceptionResult + "(" + renderedArguments + ")" + suffix));
        } catch (CompileException e) {
            var format = "Failed to compile invocation - %s: %s";
            var message = format.formatted(stack, stripped);
            return Optional.of(new Err<>(new CompileException(message, e)));
        }
    }

    private static String compileArgument(List<String> stack, String argString, int indent) throws CompileException {
        var compiledArgOptional = ValueCompiler.compile(ValueCompiler.createValueCompiler(argString, indent), stack, argString, indent);
        if (compiledArgOptional.isEmpty())
            throw new CompileException("Failed to compile argument: " + argString);

        var compiledArg = compiledArgOptional.get().$();
        return compiledArg;
    }

    private static String computeCaller(String stripped, OptionalInt callerEnd) {
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
        return caller;
    }

    static int findInvocationStart(String stripped) {
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
                index--;

                queue.pop();
                index--;
            }

            if (c == '\"') {
                while (!queue.isEmpty()) {
                    var next = queue.pop();
                    index--;
                    if (next == '\"') {
                        break;
                    }
                }

                continue;
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

    static OptionalInt computeCallerEnd(String stripped, int start) {
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

    static ArrayList<String> splitInvocationArguments(String inputArgumentStrings) {
        var inputArguments = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;
        var queue = Strings.toQueue(inputArgumentStrings);

        while (!queue.isEmpty()) {
            var c = queue.pop();
            if (c == '\'') {
                builder.append(c);
                builder.append(queue.pop());
                try {
                    builder.append(queue.pop());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                continue;
            }

            if (c == '\"') {
                builder.append(c);

                while (!queue.isEmpty()) {
                    var next = queue.pop();
                    builder.append(next);
                    if (next == '\\') {
                        builder.append(queue.pop());
                    }
                    if (next == '\"') {
                        break;
                    }
                }

                continue;
            }

            if (c == ',' && depth == 0) {
                inputArguments.add(builder.toString());
                builder = new StringBuilder();
            } else {
                if(c == '-' && queue.peek() == '>') {
                    builder.append(c);
                    builder.append(queue.pop());
                }else {
                    if (c == '(' || c == '<') depth++;
                    if (c == ')' || c == '>') depth--;
                    builder.append(c);
                }
            }
        }

        inputArguments.add(builder.toString());
        return inputArguments;
    }
}