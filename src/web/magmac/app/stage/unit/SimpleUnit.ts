import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Location } from "../../../../magmac/app/io/Location";
import { BiFunction } from "../../../../java/util/function/BiFunction";
import { Function } from "../../../../java/util/function/Function";
export class SimpleUnit<T> {
	SimpleUnit(location : Location, value : T) : public {break;break;;}
	destruct(merger : BiFunction<Location, T, R>) : R {return 0.apply( 0.location, 0.value);;}
	mapValue(mapper : Function<T, CompileResult<R>>) : CompileResult<Unit<R>> {return 0.apply( 0.value).mapValue( 0);;}
	display() : String {return 0.location.toString( );;}
}
