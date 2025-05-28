import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { Ok } from "../../../../magmac/api/result/Ok";
import { Result } from "../../../../magmac/api/result/Result";
import { CompileError } from "../../../../magmac/app/compile/error/error/CompileError";
import { BiFunction } from "../../../../java/util/function/BiFunction";
import { Function } from "../../../../java/util/function/Function";
import { Supplier } from "../../../../java/util/function/Supplier";
export class InlineCompileResult {
	temp : ?;
	InlineCompileResult(result : Result<T, CompileError>) : private;
	fromResult(result : Result<T, CompileError>) : CompileResult<T>;
	fromOk(value : T) : CompileResult<T>;
	mapValue(mapper : Function<T, R>) : CompileResult<R>;
	match(whenOk : Function<T, R>, whenErr : Function<CompileError, R>) : R;
	mapErr(mapper : Function<CompileError, CompileError>) : CompileResult<T>;
	flatMapValue(mapper : Function<T, CompileResult<R>>) : CompileResult<R>;
	and(supplier : Supplier<CompileResult<R>>) : CompileResult<Tuple2<T, R>>;
	result() : Result<T, CompileError>;
	merge(other : Supplier<CompileResult<T>>, merger : BiFunction<T, T, T>) : CompileResult<T>;
	merge(merger : BiFunction<T, T, T>, tuple : Tuple2<T, T>) : T;
}
