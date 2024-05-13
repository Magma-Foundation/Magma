package com.meti.compile;

import com.meti.result.Err;
import com.meti.result.Ok;
import com.meti.result.Result;

import java.util.List;
import java.util.Optional;

public final class SymbolCompiler {
    static Optional<Result<String, CompileException>> compile(List<String> stack, String stripped) {
        if (!Strings.isSymbol(stripped)) return Optional.empty();
        return Optional.of(SymbolCompiler.computeResult(stripped, stack));
    }

    private static Result<String, CompileException> computeResult(String input, List<String> stack) {
        if (stack.contains(input)) return new Ok<>(input);

        var format = "'%s' is not defined.";
        var message = format.formatted(input);
        return new Err<>(new CompileException(message));
    }
}