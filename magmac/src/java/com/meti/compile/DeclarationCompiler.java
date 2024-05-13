package com.meti.compile;

import com.meti.result.Err;
import com.meti.result.Ok;
import com.meti.result.Result;

import java.util.Optional;

public record DeclarationCompiler(String stripped, int indent) {
    Optional<? extends Result<String, CompileException>> compile() {
        var valueSeparator = stripped().indexOf('=');

        var headerEnd = valueSeparator == -1 ? stripped.length() : valueSeparator;
        var header = stripped().substring(0, headerEnd).strip();
        var separator = header.lastIndexOf(' ');
        var name = header.substring(separator + 1).strip();

        Result<String, CompileException> rendered;
        try {
            String valueString;
            if (valueSeparator != -1) {
                var after = stripped().substring(valueSeparator + 1).strip();
                var compiledValue = new ValueCompiler(after).compile();
                valueString = " = " + compiledValue;
            } else {
                valueString = "";
            }

            var suffix = indent == 0 ? "" : ";\n";
            rendered = new Ok<>("\t".repeat(indent) + "let " + name + valueString + suffix);
        } catch (CompileException e) {
            rendered = new Err<>(e);
        }

        return Optional.of(rendered);
    }
}