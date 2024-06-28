package magma.java;

import magma.api.collect.stream.Collector;
import magma.api.collect.stream.HeadedStream;
import magma.api.collect.stream.Stream;
import magma.api.collect.stream.Streams;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * Implementation of the List interface using Java's ArrayList.
 *
 * @param <T> the type of elements in the list
 */
public record JavaList<T>(List<T> list) implements magma.api.collect.List<T> {

    public JavaList() {
        this(Collections.emptyList());
    }

    /**
     * Creates a new List from a native Java list and a mapper function.
     *
     * @param <T>    the type of elements in the source list
     * @param <R>    the type of elements in the resulting list
     * @param frames the source list
     * @param mapper the function to apply to each element
     * @return the resulting List
     */
    public static <T, R> magma.api.collect.List<R> fromNative(List<T> frames, Function<T, R> mapper) {
        return Streams.fromNativeList(frames)
                .map(mapper)
                .collect(collecting());
    }

    /**
     * Returns a collector for creating a List.
     *
     * @param <T> the type of elements in the list
     * @return the collector
     */
    public static <T> Collector<T, magma.api.collect.List<T>> collecting() {
        return new Collector<>() {
            @Override
            public magma.api.collect.List<T> createInitial() {
                return new JavaList<>();
            }

            @Override
            public magma.api.collect.List<T> fold(magma.api.collect.List<T> current, T next) {
                return current.add(next);
            }
        };
    }

    /**
     * Creates a List from a native Java list.
     *
     * @param <T>  the type of elements in the list
     * @param list the native Java list
     * @return the resulting List
     */
    public static <T> magma.api.collect.List<T> fromNative(List<T> list) {
        return new JavaList<>(list);
    }

    /**
     * Converts a List to a native Java list.
     *
     * @param <T>    the type of elements in the list
     * @param values the List to convert
     * @return the native Java list
     */
    public static <T> List<T> toNative(magma.api.collect.List<T> values) {
        return values.stream().foldLeft(new ArrayList<>(), (ts, t) -> {
            ts.add(t);
            return ts;
        });
    }

    /**
     * Returns an empty List.
     *
     * @param <T> the type of elements in the list
     * @return the empty List
     */
    public static <T> magma.api.collect.List<T> empty() {
        return new JavaList<>();
    }

    /**
     * Creates a List with the specified elements.
     *
     * @param <T>    the type of elements in the list
     * @param values the elements to include in the list
     * @return the resulting List
     */
    @SafeVarargs
    public static <T> magma.api.collect.List<T> of(T... values) {
        return new JavaList<>(List.of(values));
    }

    @Override
    public magma.api.collect.List<T> add(T next) {
        var copy = new ArrayList<>(list);
        copy.add(next);
        return new JavaList<>(copy);
    }

    @Override
    public Stream<T> stream() {
        return new HeadedStream<>(new NativeListHead<>(list));
    }

    @Override
    public boolean contains(T element) {
        return this.list.contains(element);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public Option<T> last() {
        if (list.isEmpty()) {
            return new None<>();
        } else {
            return new Some<>(list.get(list.size() - 1));
        }
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public Option<magma.api.collect.List<T>> popLastAndDiscard() {
        if (list.isEmpty()) {
            return new None<>();
        }
        var copy = new ArrayList<>(list);
        copy.remove(copy.size() - 1);
        return new Some<>(new JavaList<>(copy));
    }

    @Override
    public magma.api.collect.List<T> pushLast(T element) {
        var copy = new ArrayList<>(list);
        copy.add(element);
        return new JavaList<>(copy);
    }

    @Override
    public Option<T> get(int index) {
        if(index < list.size()) {
            return new Some<>(list.get(index));
        }

        return new None<>();
    }
}
