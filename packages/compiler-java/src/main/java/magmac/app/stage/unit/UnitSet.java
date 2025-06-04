package magmac.app.stage.unit;

import magmac.api.iter.Iter;
import magmac.app.compile.error.CompileResult;

import java.util.function.Function;

/**
 * Collection of compilation units that can be processed together.
 */
public interface UnitSet<T> {
    Iter<T> iterValues();

    Iter<Unit<T>> iter();

    UnitSet<T> add(Unit<T> element);

    <R> CompileResult<UnitSet<R>> mapValuesToResult(Function<T, CompileResult<R>> deserializer);

    <R> UnitSet<R> mapValues(Function<T, R> serializer);
}
