package magmac.api.collect.collect;

public interface Collector<T, C> {
    C createInitial();

    C fold(C current, T element);
}
