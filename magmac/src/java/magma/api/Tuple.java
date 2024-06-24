package magma.api;

import java.util.function.Function;

public record Tuple<L, R>(L left, R right) {
    public <T> Tuple<T, R> mapLeft(Function<L, T> mapper) {
        return new Tuple<>(mapper.apply(left), right);
    }

    public <T> Tuple<L, T> mapRight(Function<R, T> mapper) {
        return new Tuple<>(left, mapper.apply(right));
    }
}
