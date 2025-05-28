package magmac.app.stage;

import magmac.app.compile.error.CompileResult;
import magmac.app.io.Location;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface Unit<T> {
    <R> R merge(BiFunction<Location, T, R> merger);

    <R> CompileResult<Unit<R>> mapValue(Function<T, CompileResult<R>> mapper);
}
