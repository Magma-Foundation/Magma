package com.meti.compile;

import com.meti.result.Err;
import com.meti.result.Ok;
import com.meti.result.Result;

import java.util.List;
import java.util.Optional;

public record DeclarationCompiler(String stripped, int indent) {
    Optional<? extends Result<String, CompileException>> compile(List<String> stack) {
        var valueSeparator = stripped.indexOf('=');

        var headerEnd = valueSeparator == -1 ? stripped.length() : valueSeparator;
        var header = stripped().substring(0, headerEnd).strip();
        var separator = header.lastIndexOf(' ');
        var name = header.substring(separator + 1).strip();
        if (!Strings.isSymbol(name)) return Optional.empty();

        var rendered = attachValue(stack, name, valueSeparator);
        return Optional.of(rendered);
    }

    private Result<String, CompileException> attachValue(List<String> stack, String name, int valueSeparator) {
        try {
            var valueString = computeValueString(stack, valueSeparator);
            var suffix = indent == 0 ? "" : ";\n";
            var s = "\t".repeat(indent) + "let " + name + valueString + suffix;
            return new Ok<>(s);
        } catch (CompileException e) {
            return new Err<>(new CompileException("Failed to compile declaration: " + stripped, e));
        }
    }

    private String computeValueString(List<String> stack, int valueSeparator) throws CompileException {
        if (valueSeparator == -1) return "";

        var after = stripped.substring(valueSeparator + 1).strip();
        var compiledValue = ValueCompiler.createValueCompiler(after, 0).compileRequired(stack);
        return " = " + compiledValue;
    }

}