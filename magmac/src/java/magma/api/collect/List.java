package magma.api.collect;

import magma.api.collect.stream.Stream;
import magma.api.option.Option;

/**
 * Interface representing a list collection with methods for adding elements,
 * streaming, and checking properties of the list.
 *
 * @param <T> the type of elements in the list
 */
public interface List<T> {

    /**
     * Adds an element to the list and returns the updated list.
     *
     * @param next the element to add
     * @return the updated list with the added element
     */
    List<T> addLast(T next);

    /**
     * Returns a stream of the elements in the list.
     *
     * @return a Stream of the list's elements
     */
    Stream<T> stream();

    /**
     * Checks if the list contains the specified element.
     *
     * @param element the element to check for
     * @return true if the list contains the element, false otherwise
     */
    boolean contains(T element);

    /**
     * Checks if the list is empty.
     *
     * @return true if the list is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Retrieves the last element in the list, if present.
     *
     * @return an Option containing the last element, or an empty Option if the list is empty
     */
    Option<T> last();

    /**
     * Returns the number of elements in the list.
     *
     * @return the size of the list
     */
    int size();

    /**
     * Removes and discards the last element of the list, returning the updated list if possible.
     *
     * @return an Option containing the updated list with the last element removed,
     * or an empty Option if the list is empty
     */
    Option<List<T>> popLastAndDiscard();

    /**
     * Adds an element to the end of the list and returns the updated list.
     *
     * @param element the element to add
     * @return the updated list with the added element
     */
    List<T> pushLast(T element);

    /**
     * Retrieves the element or None if the index is invalid.
     *
     * @param index The index.
     * @return The element.
     */
    Option<T> get(int index);

    /**
     * Adds all the items from the other list onto this list.
     *
     * @param other The other list.
     * @return The new list, with this list's items first, and the other list's items second.
     */
    List<T> addAll(List<T> other);

    /**
     * Returns the first item in this list, or None if this list is empty.
     *
     * @return The option.
     */
    Option<T> first();

    List<T> addFirst(T first);

    List<T> remove(T element);
}