package jvm.result;

import magma.result.Err;
import magma.result.Ok;
import magma.result.Result;
import magma.result.ThrowableRunnable;
import magma.result.ThrowableSupplier;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

public class Results {
    public static <T, X extends Throwable> Result<T, X> wrapSupplier(ThrowableSupplier<T, X> supplier) {
        try {
            return new Ok<>(supplier.get());
        } catch (Throwable throwable) {
            return new Err<>((X) throwable);
        }
    }

    public static <X extends Throwable> Optional<X> wrapRunnable(ThrowableRunnable<X> runnable) {
        try {
            runnable.run();
            return Optional.empty();
        } catch (Throwable throwable) {
            return Optional.of((X) throwable);
        }
    }

    public static String displayThrowable(Throwable throwable) {
        StringWriter writer = new StringWriter();
        throwable.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }
}
