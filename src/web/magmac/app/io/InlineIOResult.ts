import { Result } from "../../../magmac/api/result/Result";
import { IOException } from "../../../java/io/IOException";
import { Function } from "../../../java/util/function/Function";
export class InlineIOResult<T> {
	mapValue(mapper : Function<T, R>) : IOResult<R> {return 0;;}
	flatMapValue(mapper : Function<T, IOResult<R>>) : IOResult<R> {return 0;;}
	mapErr(mapper : Function<IOException, R>) : Result<T, R> {return 0;;}
}
