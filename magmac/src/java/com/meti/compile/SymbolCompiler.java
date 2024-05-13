package com.meti.compile;

import com.meti.result.Err;
import com.meti.result.Ok;
import com.meti.result.Result;

import java.util.List;
import java.util.Optional;

public record SymbolCompiler(String stripped, List<String> stack) {
    Optional<Result<String, CompileException>> compileSymbol() {
        if (!Strings.isSymbol(stripped())) return Optional.empty();

        Result<String, CompileException> result;
        if (stack().contains(stripped())) result = new Ok<>(stripped());
        else {
            var format = "'%s' is not defined.";
            var message = format.formatted(stripped());
            result = new Err<>(new CompileException(message));
        }

        return Optional.of(result);
    }
}