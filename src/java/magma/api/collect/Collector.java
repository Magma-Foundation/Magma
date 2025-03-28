package magma.api.collect;

public interface Collector<T, C> {
    C initial();

    C fold(C current, T next);
}
