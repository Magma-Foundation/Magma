package com.meti;

public class Options {
    public static <T> Option<T> $Option(TrySupplier<T> supplier) {
        try {
            return Some.apply(supplier.get());
        } catch (IntentionalException e) {
            return None.apply();
        }
    }
}
