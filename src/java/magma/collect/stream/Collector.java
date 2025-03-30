package magma.collect.stream;

public interface Collector<T, C> {
    C createInitial();

    C fold(C c, T t);
}
