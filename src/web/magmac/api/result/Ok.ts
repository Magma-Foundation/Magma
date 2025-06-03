import { Tuple2 } from "../../../magmac/api/Tuple2";
import { Function } from "../../../java/util/function/Function";
import { Supplier } from "../../../java/util/function/Supplier";
export class Ok<T,  X> {
	mapValue(mapper : Function<T, R>) : Result<R, X> {return 0;;}
	and(supplier : Supplier<Result<R, X>>) : Result<Tuple2<T, R>, X> {return 0;;}
	match(whenOk : Function<T, R>, whenErr : Function<X, R>) : R {return 0;;}
	flatMapValue(mapper : Function<T, Result<R, X>>) : Result<R, X> {return 0;;}
	mapErr(mapper : Function<X, R>) : Result<T, R> {return 0;;}
}
