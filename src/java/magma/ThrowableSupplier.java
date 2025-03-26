package magma;

public interface ThrowableSupplier<T, X extends Throwable> {
    T get() throws X;
}
