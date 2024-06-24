package magma.java;

import magma.api.result.Result;

public class JavaResults {
    public static <T, E extends Throwable> T unwrap(Result<T, E> result) throws E {
        var value = JavaOptionals.toNative(result.findValue());
        if (value.isPresent()) {
            return value.orElseThrow();
        }

        var error = JavaOptionals.toNative(result.findErr());
        if (error.isPresent()) {
            throw error.get();
        }

        throw new RuntimeException("Neither a value nor an error is present. This result contains nothing.");
    }
}
