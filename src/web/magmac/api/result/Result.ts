import { Tuple2 } from "../../../magmac/api/Tuple2";
import { Function } from "../../../java/util/function/Function";
import { Supplier } from "../../../java/util/function/Supplier";
export interface Result { mapper) : mapValue(Function<T, R>; supplier) : and(Supplier<Result<R, X>>;<R> R match(Function<T, R> whenOk, whenErr) : Function<X, R>; mapper) : flatMapValue(Function<T, Result<R, X>>; mapper) : mapErr(Function<X, R>;
}
