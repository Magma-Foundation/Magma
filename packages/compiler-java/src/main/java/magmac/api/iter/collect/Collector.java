package magmac.api.iter.collect;

/**
 * Fold-style collector used by {@link magmac.api.iter.Iter#collect}.
 */

public interface Collector<T, C> {
    /**
     * Provides the initial accumulation value.
     */
    C createInitial();

    /**
     * Accumulates {@code element} into {@code current}.
     */
    C fold(C current, T element);
}
