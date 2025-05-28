import { Tuple2 } from "../../../magmac/api/Tuple2";
import { Function } from "../../../java/util/function/Function";
import { Supplier } from "../../../java/util/function/Supplier";
export class Ok {
	public mapValue( mapper() : Function<T, R>) : Result<R, X> {
		return new Ok<>( mapper.apply( this.value));
	}
	public and( supplier() : Supplier<Result<R, X>>) : Result<Tuple2<T, R>, X> {
		return supplier.get( ).mapValue( ( otherValue() : R) => new Tuple2<>( this.value, otherValue));
	}
	public match( whenOk() : Function<T, R>,  whenErr() : Function<X, R>) : R {
		return whenOk.apply( this.value);
	}
	public flatMapValue( mapper() : Function<T, Result<R, X>>) : Result<R, X> {
		return mapper.apply( this.value);
	}
	public mapErr( mapper() : Function<X, R>) : Result<T, R> {
		return new Ok<>( this.value);
	}
}
