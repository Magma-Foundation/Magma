package com.meti;

import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record MCCompiler(Input input) {
    String compile() throws CompileException {
        return lexInteger()
                .orElseThrow(() -> new CompileException("Invalid input."));
    }

    private Option<String> lexInteger() {
        try {
            var value = input.compute();
            Integer.parseInt(value);
            return new Some<>(value);
        } catch (NumberFormatException e) {
            return new None<String>();
        }
    }
}