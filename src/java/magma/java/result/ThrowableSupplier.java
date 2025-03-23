package magma.java.result;

public interface ThrowableSupplier<T, X extends Throwable> {
    T get() throws X;
}
