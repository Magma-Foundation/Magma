import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { Map } from "../../../../magmac/api/collect/map/Map";
import { MapCollector } from "../../../../magmac/api/collect/map/MapCollector";
import { Maps } from "../../../../magmac/api/collect/map/Maps";
import { Iter } from "../../../../magmac/api/iter/Iter";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileResultCollector } from "../../../../magmac/app/compile/error/CompileResultCollector";
import { Location } from "../../../../magmac/app/io/Location";
import { Function } from "../../../../java/util/function/Function";
export class MapUnitSet<T> {
	MapUnitSet() : public {0( 0.empty( ));;}
	iter() : Iter<Unit<T>> {return 0.roots.iter( ).map( 0);;}
	add(element : Unit<T>) : UnitSet<T> {return new MapUnitSet<>( 0.destruct( 0.roots.put));;}
	mapValuesToResult(deserializer : Function<T, CompileResult<R>>) : CompileResult<UnitSet<R>> {return 0.roots.iter( ).map( 0).collect( new CompileResultCollector<>( new MapCollector<>( ))).mapValue( 0.new);;}
	mapValues(serializer : Function<T, R>) : UnitSet<R> {return new MapUnitSet<>( 0.roots.iter( ).map( 0).collect( new MapCollector<>( )));;}
	iterValues() : Iter<T> {return 0.roots.iter( ).map( 0.right);;}
}
