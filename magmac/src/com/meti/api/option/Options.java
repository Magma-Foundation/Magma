package com.meti.api.option;

public class Options {
    public static <T> Option<T> $Option(TrySupplier<T, IntentionalException> supplier) {
        try {
            return Some.apply(supplier.get());
        } catch (IntentionalException e) {
            return None.apply();
        }
    }

    public static <T> T $$() throws IntentionalException {
        throw new IntentionalException();
    }
}
