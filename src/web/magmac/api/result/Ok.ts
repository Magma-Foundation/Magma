import { Tuple2 } from "../../../magmac/api/Tuple2";
import { Function } from "../../../java/util/function/Function";
import { Supplier } from "../../../java/util/function/Supplier";
export class Ok<T,  X> {
	mapValue(mapper : Function<T, R>) : Result<R, X> {return new Ok<>( 0.apply( 0.value));;}
	and(supplier : Supplier<Result<R, X>>) : Result<Tuple2<T, R>, X> {return 0.get( ).mapValue( 0);;}
	match(whenOk : Function<T, R>, whenErr : Function<X, R>) : R {return 0.apply( 0.value);;}
	flatMapValue(mapper : Function<T, Result<R, X>>) : Result<R, X> {return 0.apply( 0.value);;}
	mapErr(mapper : Function<X, R>) : Result<T, R> {return new Ok<>( 0.value);;}
}
