package magmac.api.iter.collect;

public interface Collector<T, C> {
    C createInitial();

    C fold(C current, T element);
}
