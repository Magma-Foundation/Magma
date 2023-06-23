package com.meti;

import com.meti.safe.NativeString;

public record Resolver(NativeString input) {
    public NativeString resolve() {
        return this.input
                .firstIndexOfCharByPredicate(Character::isLetter)
                .match(input::sliceFrom, () -> NativeString.from("i16"));
    }
}