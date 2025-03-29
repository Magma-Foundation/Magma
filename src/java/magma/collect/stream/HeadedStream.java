package magma.collect.stream;

import magma.option.Tuple;

import java.util.function.BiFunction;
import java.util.function.Function;

public record HeadedStream<T>(Head<T> head) implements Stream<T> {
    @Override
    public <R> R fold(R initial, BiFunction<R, T, R> folder) {
        R current = initial;
        while (true) {
            R finalCurrent = current;
            Tuple<Boolean, R> tuple = head.next()
                    .map(inner -> folder.apply(finalCurrent, inner))
                    .toTuple(current);

            if (tuple.left()) {
                current = tuple.right();
            } else {
                break;
            }
        }

        return current;
    }

    @Override
    public <R> Stream<R> map(Function<T, R> mapper) {
        return new HeadedStream<>(() -> head.next().map(mapper));
    }
}
