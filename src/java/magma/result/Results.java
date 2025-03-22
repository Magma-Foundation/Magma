package magma.result;

import java.util.Optional;

public class Results {
    public static <T, X extends Throwable> Result<T, X> wrapSupplier(ThrowableSupplier<T, X> supplier) {
        try {
            return new Ok<>(supplier.get());
        } catch (Throwable throwable) {
            //noinspection unchecked
            return new Err<>((X) throwable);
        }
    }

    public static <X extends Throwable> Optional<X> wrapRunnable(ThrowableRunnable<X> runnable) {
        try {
            runnable.run();
            return Optional.empty();
        } catch (Throwable throwable) {
            //noinspection unchecked
            return Optional.of((X) throwable);
        }
    }
}
