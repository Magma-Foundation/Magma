package magma.api.contain.collect;

/**
 * Interface representing a collector that accumulates elements into a collection.
 *
 * @param <T> the type of elements to be collected
 * @param <C> the type of the collection
 */
public interface Collector<T, C> {

    /**
     * Creates the initial empty collection.
     *
     * @return the initial empty collection
     */
    C createInitial();

    /**
     * Adds an element to the current collection and returns the updated collection.
     *
     * @param current the current collection
     * @param next    the element to add
     * @return the updated collection with the added element
     */
    C fold(C current, T next);
}