import { Result } from "../../../magmac/api/result/Result";
import { IOException } from "../../../java/io/IOException";
import { Function } from "../../../java/util/function/Function";
export class InlineIOResult {
	mapValue(mapper : Function<T, R>) : IOResult<R> {
		return new InlineIOResult<>( this.result.mapValue( mapper));
	}
	flatMapValue(mapper : Function<T, IOResult<R>>) : IOResult<R> {
		return new InlineIOResult<>( this.result.flatMapValue( (T value) ->mapper.apply( value).result( )));
	}
	mapErr(mapper : Function<IOException, R>) : Result<T, R> {
		return this.result.mapErr( mapper);
	}
}
