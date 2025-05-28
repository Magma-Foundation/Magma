import { Result } from "../../../magmac/api/result/Result";
import { IOException } from "../../../java/io/IOException";
import { Function } from "../../../java/util/function/Function";
export class InlineIOResult {
	mapValue : IOResult<R> {
	}
	flatMapValue : IOResult<R> {
	}
	mapErr : Result<T, R> {
	}
}
