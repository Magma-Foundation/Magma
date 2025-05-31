import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Location } from "../../../../magmac/app/io/Location";
import { BiFunction } from "../../../../java/util/function/BiFunction";
import { Function } from "../../../../java/util/function/Function";
export class SimpleUnit<T> {
	SimpleUnit(location : Location, value : T) : public;
	destruct(merger : BiFunction<Location, T, R>) : R;
	mapValue(mapper : Function<T, CompileResult<R>>) : CompileResult<Unit<R>>;
	display() : String;
}
