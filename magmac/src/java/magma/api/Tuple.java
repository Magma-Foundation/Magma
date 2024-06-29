package magma.api;

import java.util.function.Function;

/**
 * A simple record representing a tuple with a left and right value.
 *
 * @param <L> the type of the left value
 * @param <R> the type of the right value
 */
public record Tuple<L, R>(L left, R right) {

    /**
     * Applies a function to the left value of the tuple, returning a new tuple with the transformed left value.
     *
     * @param <T> the type of the transformed left value
     * @param mapper the function to apply to the left value
     * @return a new Tuple with the transformed left value and the same right value
     */
    public <T> Tuple<T, R> mapLeft(Function<L, T> mapper) {
        return new Tuple<>(mapper.apply(left), right);
    }
}
