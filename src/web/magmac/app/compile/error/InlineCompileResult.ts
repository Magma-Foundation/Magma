import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { Result } from "../../../../magmac/api/result/Result";
import { CompileError } from "../../../../magmac/app/compile/error/error/CompileError";
import { BiFunction } from "../../../../java/util/function/BiFunction";
import { Function } from "../../../../java/util/function/Function";
import { Supplier } from "../../../../java/util/function/Supplier";
export class InlineCompileResult<T> {
	mapValue(mapper : Function<T, R>) : CompileResult<R> {break;;}
	match(whenOk : Function<T, R>, whenErr : Function<CompileError, R>) : R {break;;}
	mapErr(mapper : Function<CompileError, CompileError>) : CompileResult<T> {break;;}
	flatMapValue(mapper : Function<T, CompileResult<R>>) : CompileResult<R> {break;;}
	and(supplier : Supplier<CompileResult<R>>) : CompileResult<Tuple2<T, R>> {break;;}
	merge(other : Supplier<CompileResult<T>>, merger : BiFunction<T, T, T>) : CompileResult<T> {break;;}
	merge(merger : BiFunction<T, T, T>, tuple : Tuple2<T, T>) : T {break;break;break;;}
}
