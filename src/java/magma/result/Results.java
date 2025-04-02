package magma.result;

import java.util.Optional;

public class Results {
    public static <T, X extends Throwable> T unwrap(Result<T, X> result) throws X {
        Optional<T> maybeValue = result.findValue();
        if (maybeValue.isPresent()) return maybeValue.get();

        Optional<X> maybeError = result.findError();
        if (maybeError.isPresent()) throw maybeError.get();

        throw new RuntimeException("Neither a value nor an error is present.");
    }
}
