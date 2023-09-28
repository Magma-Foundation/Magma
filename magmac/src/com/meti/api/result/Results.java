package com.meti.api.result;

import com.meti.api.option.TrySupplier;

public class Results {
    private Results() {
    }

    public static <T, E extends Throwable> Result<T, E> $Result(TrySupplier<T, E> supplier) {
        try {
            return Ok.apply(supplier.get());
        } catch (Throwable e) {
            //noinspection unchecked
            return Err.apply((E) e);
        }
    }
}
