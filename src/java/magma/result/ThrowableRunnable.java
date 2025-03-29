package magma.result;

public interface ThrowableRunnable<T extends Throwable> {
    void run() throws T;
}
