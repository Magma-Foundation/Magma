import { CompileResult } from "../../../magmac/app/compile/error/CompileResult";
import { Location } from "../../../magmac/app/io/Location";
import { BiFunction } from "../../../java/util/function/BiFunction";
import { Function } from "../../../java/util/function/Function";
export class SimpleUnit {private final location : Location;private final value : T;
	 SimpleUnit( location : Location,  value : T) : public {
		this.location=location;
		this.value=value;
	}
	public deconstruct( merger : BiFunction<Location, T, R>) : R {
		return merger.apply( this.location, this.value);
	}
	public mapValue( mapper : Function<T, CompileResult<R>>) : CompileResult<Unit<R>> {
		return mapper.apply( this.value).mapValue( ( root : R) => new SimpleUnit<>( this.location, root));
	}
}
