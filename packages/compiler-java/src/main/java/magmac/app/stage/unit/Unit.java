package magmac.app.stage.unit;

import magmac.app.compile.error.CompileResult;
import magmac.app.io.Location;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A single compilation unit paired with its source location.
 */
public interface Unit<T> {
    <R> R destruct(BiFunction<Location, T, R> merger);

    <R> CompileResult<Unit<R>> mapValue(Function<T, CompileResult<R>> mapper);

    String display();
}
