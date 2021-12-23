package com.meti;

import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record IntegerLexer(String input) {
    Option<String> lex() {
        try {
            Integer.parseInt(input);
            return new Some<>(input);
        } catch (NumberFormatException e) {
            return new None<>();
        }
    }
}
