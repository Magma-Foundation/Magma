import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { Result } from "../../../../magmac/api/result/Result";
import { CompileError } from "../../../../magmac/app/compile/error/error/CompileError";
import { BiFunction } from "../../../../java/util/function/BiFunction";
import { Function } from "../../../../java/util/function/Function";
import { Supplier } from "../../../../java/util/function/Supplier";
export interface CompileResult {merger) : BiFunction<T, T, T>;mapper) : mapValue(Function<T, R>;whenErr) : Function<CompileError, R>;mapper) : mapErr(Function<CompileError, CompileError>;mapper) : flatMapValue(Function<T, CompileResult<R>>;supplier) : and(Supplier<CompileResult<R>>;result() : Result<T, CompileError>;
}
