package magma;

public interface ThrowableRunnable<T extends Throwable> {
    void run() throws T;
}
