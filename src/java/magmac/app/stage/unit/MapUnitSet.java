package magmac.app.stage.unit;

import magmac.api.Tuple2;
import magmac.api.collect.map.Map;
import magmac.api.collect.map.MapCollector;
import magmac.api.collect.map.Maps;
import magmac.api.iter.Iter;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResultCollector;
import magmac.app.io.Location;

import java.util.function.Function;

public record MapUnitSet<T>(Map<Location, T> roots) implements UnitSet<T> {
    public MapUnitSet() {
        this(Maps.empty());
    }

    @Override
    public Iter<Unit<T>> iter() {
        return this.roots.iter().map((Tuple2<Location, T> entry) -> new SimpleUnit<>(entry.left(), entry.right()));
    }

    @Override
    public UnitSet<T> add(Unit<T> element) {
        return new MapUnitSet<>(element.destruct(this.roots::put));
    }

    @Override
    public <R> CompileResult<UnitSet<R>> mapValuesToResult(Function<T, CompileResult<R>> deserializer) {
        return this.roots.iter()
                .map((Tuple2<Location, T> tuple) -> deserializer.apply(tuple.right()).mapValue((R apply) -> new Tuple2<>(tuple.left(), apply)))
                .collect(new CompileResultCollector<>(new MapCollector<>()))
                .mapValue(MapUnitSet::new);
    }

    @Override
    public <R> UnitSet<R> mapValues(Function<T, R> serializer) {
        return new MapUnitSet<>(this.roots.iter()
                .map((Tuple2<Location, T> tuple) -> new Tuple2<>(tuple.left(), serializer.apply(tuple.right())))
                .collect(new MapCollector<>()));
    }

    @Override
    public Iter<T> iterValues() {
        return this.roots.iter().map(Tuple2::right);
    }
}
