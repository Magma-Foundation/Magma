package com.meti.iterate;

import com.meti.core.Option;
import com.meti.core.Result;
import com.meti.core.Tuple;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Iterator<T> {

    /**
     * Applies a binary function to a start value and all elements of this iterator, going left to right.
     *
     * @param initial the start value.
     * @param folder  the binary function.
     * @param <C>     the result type.
     * @param <E>     the exception type that may be thrown during the operation.
     * @return the result of the fold operation.
     */
    <C, E extends Throwable> Result<C, E> foldLeftToResult(C initial, BiFunction<C, T, Result<C, E>> folder);

    /**
     * Applies a binary function to a start value and all elements of this iterator, going left to right.
     *
     * @param initial the start value.
     * @param folder  the binary function.
     * @param <C>     the result type.
     * @return the result of the fold operation.
     */
    <C> C foldLeft(C initial, BiFunction<C, T, C> folder);

    /**
     * Transforms the elements of the iterator using the provided function.
     *
     * @param mapper the function to transform elements.
     * @param <R>    the type of the resulting iterator.
     * @return a new iterator with the transformed elements.
     */
    <R> Iterator<R> map(Function<T, R> mapper);

    /**
     * Transforms the iterator into another type using the provided function.
     *
     * @param mapper the function to transform the iterator.
     * @param <R>    the result type.
     * @return the transformed object.
     */
    <R> R into(Function<Iterator<T>, R> mapper);

    /**
     * Retrieves the head of the iterator, if it exists.
     *
     * @return an option containing the head of the iterator, or None if empty.
     */
    Option<T> head();

    /**
     * Collects the elements of the iterator using the specified collector.
     *
     * @param collector the collector to use.
     * @param <C>       the result type of the collection.
     * @return the collected result.
     */
    <C> C collect(Collector<T, C> collector);

    /**
     * Transforms each element of the iterator into an iterator and flattens the result.
     *
     * @param mapper the function to transform elements into iterators.
     * @param <R>    the type of the resulting iterator.
     * @return a new iterator with the transformed and flattened elements.
     */
    <R> Iterator<R> flatMap(Function<T, Iterator<R>> mapper);

    /**
     * Returns an iterator containing only the elements that satisfy the given predicate.
     *
     * @param predicate the predicate to apply to each element.
     * @return an iterator containing only the matching elements.
     */
    Iterator<T> filter(Predicate<T> predicate);

    /**
     * Returns an iterator containing the first 'count' elements from the original iterator.
     *
     * @param count the number of elements to take from this iterator.
     * @return an iterator containing only the first 'count' elements.
     */
    Iterator<T> take(int count);

    /**
     * Performs the given action for each element of the iterator.
     *
     * @param consumer the action to perform on each element.
     */
    void forEach(Consumer<T> consumer);

    /**
     * Partitions this iterator into a new iterator according to a discriminator function.
     *
     * @param mapper the function to compute the discriminator value.
     * @param <P>    the type of the discriminator values.
     * @param <R>    the type of the resulting iterator.
     * @return an object representing the partitioned iterator.
     */
    <P, R> Unzip<T, P, R> unzip(Function<T, P> mapper);

    /**
     * Returns an iterator that concatenates this iterator with another one.
     *
     * @param other the other iterator.
     * @return an iterator that concatenates this iterator with 'other'.
     */
    Iterator<T> then(Iterator<T> other);

    /**
     * Returns whether any elements of this iterator satisfy the given predicate.
     *
     * @param predicate the predicate to apply to elements.
     * @return true if any elements match the predicate, false otherwise.
     */
    boolean anyMatch(Predicate<T> predicate);

    /**
     * Returns whether all elements of this iterator satisfy the given predicate.
     *
     * @param predicate the predicate to apply to elements.
     * @return true if all elements match the predicate, false otherwise.
     */
    boolean allMatch(Predicate<T> predicate);

    /**
     * Returns an iterator of tuples where each tuple contains corresponding elements of this iterator and the provided one.
     *
     * @param entries the other iterator.
     * @param <R>     the type of elements in the other iterator.
     * @return an iterator of tuples.
     */
    <R> Iterator<Tuple<T, R>> zip(Iterator<R> entries);

    /**
     * Returns an iterator containing only distinct elements according to their natural equality.
     *
     * @return an iterator containing only distinct elements.
     */
    Iterator<T> distinct();
}


