package magmac.api.collect.list;

import magmac.api.Option;
import magmac.api.Tuple2;
import magmac.api.iter.Iter;

import java.util.function.BiFunction;

/**
 * Immutable list abstraction.
 */

public interface List<T> {
    /**
     * Returns a new list with {@code element} appended to the end.
     */
    List<T> addLast(T element);

    /**
     * Creates an iterator over the elements.
     */
    Iter<T> iter();

    /**
     * Appends all elements from {@code others}.
     */
    List<T> addAllLast(List<T> others);

    /**
     * Removes all occurrences found in {@code others}.
     */
    List<T> removeAll(List<T> others);

    /**
     * Retrieves the element at {@code index}.
     */
    T get(int index);

    /**
     * Sorts using the provided comparator.
     */
    List<T> sort(BiFunction<T, T, Integer> sorter);

    /**
     * Returns the last element if present.
     */
    Option<T> findLast();

    /**
     * Checks if {@code element} exists in the list.
     */
    boolean contains(T element);

    /**
     * Number of elements in the list.
     */
    int size();

    /**
     * Removes and returns the last element.
     */
    Option<Tuple2<List<T>, T>> popLast();

    /**
     * Removes and returns the first element.
     */
    Option<Tuple2<T, List<T>>> popFirst();

    /**
     * Returns a new list with {@code element} added at the front.
     */
    List<T> addFirst(T element);

    /**
     * Checks whether the list has no elements.
     */
    boolean isEmpty();

    /**
     * Iterates over the list along with element indices.
     */
    Iter<Tuple2<Integer, T>> iterWithIndices();
}
