package com.meti.compile;

import com.meti.api.result.Err;
import com.meti.api.result.Ok;
import com.meti.api.result.Result;

import java.util.List;
import java.util.Optional;

public final class SymbolCompiler {
    static Optional<Result<String, CompileException>> compile(List<String> stack, String stripped) {
        if (!Strings.isSymbol(stripped)) return Optional.empty();
        return Optional.of(SymbolCompiler.computeResult(stripped, stack));
    }

    private static Result<String, CompileException> computeResult(String input, List<String> stack) {
        return new Ok<>(input);

/*        if (stack.contains(input)) return new Ok<>(input);

        try {
            Class.forName("java.lang." + input);

            return new Ok<>(input);
        } catch (ClassNotFoundException e) {
            var format = "'%s' is not defined: %s";
            var message = format.formatted(input, stack);
            return new Err<>(new CompileException(message));
        }*/
    }
}