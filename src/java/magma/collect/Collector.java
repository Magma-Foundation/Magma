package magma.collect;

public interface Collector<T, C> {
    C createInitial();

    C fold(C c, T t);
}
