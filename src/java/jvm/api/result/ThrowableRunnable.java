package jvm.api.result;

public interface ThrowableRunnable<X extends Throwable> {
    void run() throws X;
}
