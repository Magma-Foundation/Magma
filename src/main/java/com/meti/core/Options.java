package com.meti.core;

public class Options {
    private Options() {
    }

    public static <T> Option<T> $Option(Generator<T, IntentionalException> generator) {
        try {
            return new Some<>(generator.generate());
        } catch (IntentionalException e) {
            return None.apply();
        }
    }
}
