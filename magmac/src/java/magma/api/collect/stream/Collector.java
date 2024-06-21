package magma.api.collect.stream;

public interface Collector<T, C> {
    C createInitial();

    C fold(C current, T next);
}
