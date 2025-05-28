import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { Result } from "../../../../magmac/api/result/Result";
import { CompileError } from "../../../../magmac/app/compile/error/error/CompileError";
import { BiFunction } from "../../../../java/util/function/BiFunction";
import { Function } from "../../../../java/util/function/Function";
import { Supplier } from "../../../../java/util/function/Supplier";
export interface CompileResult {CompileResult<T> merge(Supplier<CompileResult<T>> other, merger)() : BiFunction<T, T, T>; mapper)() : mapValue(Function<T, R>;<R> R match(Function<T, R> whenOk, whenErr)() : Function<CompileError, R>;CompileResult mapper)() : mapErr(Function<CompileError, CompileError>; mapper)() : flatMapValue(Function<T, CompileResult<R>>; supplier)() : and(Supplier<CompileResult<R>>; result()() : Result<T, CompileError>;
}
