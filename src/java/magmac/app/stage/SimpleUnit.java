package magmac.app.stage;

import magmac.app.compile.error.CompileResult;
import magmac.app.io.Location;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class SimpleUnit<T> implements Unit<T> {
    private final Location location;
    private final T value;

    public SimpleUnit(Location location, T value) {
        this.location = location;
        this.value = value;
    }

    @Override
    public <R> R merge(BiFunction<Location, T, R> merger) {
        return merger.apply(this.location, this.value);
    }

    @Override
    public <R> CompileResult<Unit<R>> mapValue(Function<T, CompileResult<R>> mapper) {
        return mapper.apply(this.value).mapValue((R root) -> new SimpleUnit<>(this.location, root));
    }
}
