package magmac.api.collect.head;

import magmac.api.collect.Iter;
import magmac.api.result.Ok;
import magmac.api.result.Result;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public record HeadedIter<T>(Head<T> head) implements Iter<T> {
    @Override
    public <R, X> Result<R, X> foldToResult(R initial, BiFunction<R, T, Result<R, X>> folder) {
        return this.<Result<R, X>>fold(new Ok<>(initial),
                (rxResult, t) -> rxResult.flatMapValue(
                        r -> folder.apply(r, t)));
    }

    @Override
    public <R> Iter<R> map(Function<T, R> mapper) {
        return new HeadedIter<>(() -> this.head.next().map(mapper));
    }

    private <R> R fold(R initial, BiFunction<R, T, R> folder) {
        R current = initial;
        while (true) {
            R finalCurrent = current;
            Optional<R> optional = this.head.next().map(next -> folder.apply(finalCurrent, next));
            if (optional.isPresent()) {
                current = optional.get();
            }
            else {
                return current;
            }
        }
    }
}
