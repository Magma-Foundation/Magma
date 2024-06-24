package magma.java;

public interface JavaRunnable<E extends Throwable> {
    void execute() throws E;
}
