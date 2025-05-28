import { Result } from "../../../magmac/api/result/Result";
import { IOException } from "../../../java/io/IOException";
import { Function } from "../../../java/util/function/Function";
export interface IOResult { mapper) : mapValue(Function<T, R>; mapper) : flatMapValue(Function<T, IOResult<R>>; mapper) : mapErr(Function<IOException, R>; result() : Result<T, IOException>;
}
