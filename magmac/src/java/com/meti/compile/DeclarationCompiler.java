package com.meti.compile;

import com.meti.api.result.Err;
import com.meti.api.result.Ok;
import com.meti.api.result.Result;

import java.util.List;
import java.util.Optional;

public record DeclarationCompiler(String stripped, int indent) {
    static Optional<? extends Result<String, CompileException>> compile(List<String> stack, String input, int indent) {
        var valueSeparator = input.indexOf('=');

        var headerEnd = valueSeparator == -1 ? input.length() : valueSeparator;
        var header = input.substring(0, headerEnd).strip();
        var separator = header.lastIndexOf(' ');
        var name = header.substring(separator + 1).strip();
        if (!Strings.isSymbol(name)) return Optional.empty();

        var rendered = attachValue(stack, name, valueSeparator, indent, input);
        return Optional.of(rendered);
    }

    private static Result<String, CompileException> attachValue(List<String> stack, String name, int valueSeparator, int indent, String input) {
        try {
            var valueString = computeValueString(input, stack, valueSeparator);
            stack.add(name);

            var suffix = indent == 0 ? "" : ";\n";
            var s = "\t".repeat(indent) + "let " + name + valueString + suffix;
            return new Ok<>(s);
        } catch (CompileException e) {
            var format = "Failed to compile declaration - %s: %s";
            var message = format.formatted(stack, input);
            return new Err<>(new CompileException(message, e));
        }
    }

    private static String computeValueString(String stripped, List<String> stack, int valueSeparator) throws CompileException {
        if (valueSeparator == -1) return "";

        var after = stripped.substring(valueSeparator + 1).strip();
        var compiledValue = ValueCompiler.compileRequired(ValueCompiler.createValueCompiler(after, 0), stack);
        return " = " + compiledValue;
    }

}