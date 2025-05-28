import { Tuple2 } from "../../../magmac/api/Tuple2";
import { Function } from "../../../java/util/function/Function";
import { Supplier } from "../../../java/util/function/Supplier";
export class Err<T,  X> {
	public mapValue( mapper : Function<T, R>) : Result<R, X> {
		return new Err<>( this.error);
	}
	public and( supplier : Supplier<Result<R, X>>) : Result<Tuple2<T, R>, X> {
		return new Err<>( this.error);
	}
	public match( whenOk : Function<T, R>,  whenErr : Function<X, R>) : R {
		return whenErr.apply( this.error);
	}
	public flatMapValue( mapper : Function<T, Result<R, X>>) : Result<R, X> {
		return new Err<>( this.error);
	}
	public mapErr( mapper : Function<X, R>) : Result<T, R> {
		return new Err<>( mapper.apply( this.error));
	}
}
