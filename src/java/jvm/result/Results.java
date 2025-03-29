package jvm.result;

import magma.option.None;
import magma.option.Option;
import magma.option.Some;
import magma.result.Err;
import magma.result.Ok;
import magma.result.Result;
import magma.result.ThrowableRunnable;
import magma.result.ThrowableSupplier;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Results {
    public static <T, X extends Throwable> Result<T, X> wrapSupplier(ThrowableSupplier<T, X> supplier) {
        try {
            return new Ok<>(supplier.get());
        } catch (Throwable error) {
            //noinspection unchecked
            return new Err<>((X) error);
        }
    }

    public static <T, X extends Throwable> T unwrap(Result<T, X> result) throws X {
        Option<T> maybeValue = result.findValue();
        if (maybeValue.isPresent()) return maybeValue.orElse(null);

        Option<X> maybeError = result.findError();
        if (maybeError.isPresent()) throw maybeError.orElse(null);

        throw new RuntimeException("Neither a value nor an error is present.");
    }

    public static <X extends Throwable> Option<X> wrapRunnable(ThrowableRunnable<X> runnable) {
        try {
            runnable.run();
            return new None<>();
        } catch (Throwable e) {
            //noinspection unchecked
            return new Some<>((X) e);
        }
    }

    public static String printStackTraceToString(Throwable error) {
        StringWriter writer = new StringWriter();
        error.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }
}
