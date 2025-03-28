package magma.result;

import magma.java.Options;

import java.util.Optional;

public class Results {
    public static <T, X extends Throwable> Result<T, X> wrap(ThrowableSupplier<T, X> supplier) {
        try {
            return new Ok<>(supplier.get());
        } catch (Throwable e) {
            //noinspection unchecked
            return new Err<>((X) e);
        }
    }

    public static <T, X extends Throwable> T unwrap(Result<T, X> result) throws X {
        Optional<T> maybeValue = Options.unwrap(result.findValue());
        if (maybeValue.isPresent()) return maybeValue.get();

        Optional<X> maybeError = Options.unwrap(result.findError());
        if (maybeError.isPresent()) throw maybeError.get();

        throw new RuntimeException("Neither a value nor an error is present.");
    }
}
