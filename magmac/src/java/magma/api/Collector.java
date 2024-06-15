package magma.api;

public interface Collector<T, C> {
    C createInitial();

    C fold(C current, T next);
}
