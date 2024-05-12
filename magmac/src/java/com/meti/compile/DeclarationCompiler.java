package com.meti.compile;

import com.meti.result.Err;
import com.meti.result.Ok;
import com.meti.result.Result;

import java.util.Optional;

public record DeclarationCompiler(String stripped) {
    Optional<? extends Result<String, CompileException>> compile() {
        var valueSeparator = stripped().indexOf('=');
        if (valueSeparator == -1) return Optional.empty();

        var before = stripped().substring(0, valueSeparator).strip();
        var separator = before.lastIndexOf(' ');
        var name = before.substring(separator + 1).strip();

        var after = stripped().substring(valueSeparator + 1).strip();
        Result<String, CompileException> rendered;
        try {
            var compiledValue = new ValueCompiler(after).compile();
            rendered = new Ok<>("let " + name + " = " + compiledValue);
        } catch (CompileException e) {
            rendered = new Err<>(e);
        }

        return Optional.of(rendered);
    }
}