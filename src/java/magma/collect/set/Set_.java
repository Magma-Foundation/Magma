package magma.collect.set;

import magma.collect.stream.Stream;

import java.util.function.Consumer;

public interface Set_<T> {
    Stream<T> stream();

    Set_<T> add(T element);

    void forEach(Consumer<T> consumer);
}
