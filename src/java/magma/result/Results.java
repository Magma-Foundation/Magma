package magma.result;

import magma.option.JavaOptions;

import java.util.Optional;

public class Results {
    public static <T, X extends Throwable> Result<T, X> wrap(ThrowableSupplier<T, X> supplier) {
        try {
            return new Ok<>(supplier.get());
        } catch (Throwable t) {
            //noinspection unchecked
            return new Err<>((X) t);
        }
    }

    public static <T, X extends Throwable> T unwrap(Result<T, X> result) throws X {
        Optional<T> maybeValue = result.findValue().into(JavaOptions::toNative);
        if (maybeValue.isPresent()) return maybeValue.get();

        Optional<X> maybeError = result.findError().into(JavaOptions::toNative);
        if (maybeError.isPresent()) throw maybeError.get();

        throw new RuntimeException("Neither a value nor an error is present.");
    }
}
