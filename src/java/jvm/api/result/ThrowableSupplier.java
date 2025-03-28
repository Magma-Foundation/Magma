package jvm.api.result;

public interface ThrowableSupplier<T, X extends Throwable> {
    T get() throws X;
}
