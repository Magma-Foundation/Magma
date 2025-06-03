import { Tuple2 } from "../../../magmac/api/Tuple2";
import { Function } from "../../../java/util/function/Function";
import { Supplier } from "../../../java/util/function/Supplier";
export class Err<T,  X> {
	mapValue(mapper : Function<T, R>) : Result<R, X> {return new Err<>( this.error);;}
	and(supplier : Supplier<Result<R, X>>) : Result<Tuple2<T, R>, X> {return new Err<>( this.error);;}
	match(whenOk : Function<T, R>, whenErr : Function<X, R>) : R {return whenErr.apply( this.error);;}
	flatMapValue(mapper : Function<T, Result<R, X>>) : Result<R, X> {return new Err<>( this.error);;}
	mapErr(mapper : Function<X, R>) : Result<T, R> {return new Err<>( mapper.apply( this.error));;}
}
