package magma.api.result;

import java.util.function.Function;

public record Err<T, X>(X error) implements Result<T, X> {
    @Override
    public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
        return whenErr.apply(this.error);
    }
}
