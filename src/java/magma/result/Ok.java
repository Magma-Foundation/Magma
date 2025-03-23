package magma.result;

import magma.option.None;
import magma.option.Option;
import magma.option.Some;

import java.util.Optional;

public record Ok<T, X>(T value) implements Result<T, X> {
    private Optional<T> findValue0() {
        return Optional.of(value);
    }

    private Optional<X> findError0() {
        return Optional.empty();
    }

    @Override
    public Option<T> findValue() {
        return findValue0().<Option<T>>map(Some::new).orElseGet(None::new);
    }

    @Override
    public Option<X> findError() {
        return findError0().<Option<X>>map(Some::new).orElseGet(None::new);
    }

    @Override
    public boolean isOk() {
        return true;
    }
}
