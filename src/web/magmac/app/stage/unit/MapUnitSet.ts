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
	MapUnitSet() : public {break;;}
	iter() : Iter<Unit<T>> {return 0;;}
	add(element : Unit<T>) : UnitSet<T> {return 0;;}
	mapValuesToResult(deserializer : Function<T, CompileResult<R>>) : CompileResult<UnitSet<R>> {return 0;;}
	mapValues(serializer : Function<T, R>) : UnitSet<R> {return 0;;}
	iterValues() : Iter<T> {return 0;;}
}
