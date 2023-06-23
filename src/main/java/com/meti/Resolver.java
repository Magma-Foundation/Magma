package com.meti;

public record Resolver(NativeString input) {
    NativeString resolve() {
        return this.input
                .firstIndexOfCharByPredicate(Character::isLetter)
                .match(input::sliceTo, () -> input);
    }
}