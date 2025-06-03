import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { Result } from "../../../../magmac/api/result/Result";
import { CompileError } from "../../../../magmac/app/compile/error/error/CompileError";
import { BiFunction } from "../../../../java/util/function/BiFunction";
import { Function } from "../../../../java/util/function/Function";
import { Supplier } from "../../../../java/util/function/Supplier";
export class InlineCompileResult<T> {
	mapValue(mapper : Function<T, R>) : CompileResult<R> {return 0;;}
	match(whenOk : Function<T, R>, whenErr : Function<CompileError, R>) : R {return 0;;}
	mapErr(mapper : Function<CompileError, CompileError>) : CompileResult<T> {return 0;;}
	flatMapValue(mapper : Function<T, CompileResult<R>>) : CompileResult<R> {return 0;;}
	and(supplier : Supplier<CompileResult<R>>) : CompileResult<Tuple2<T, R>> {return 0;;}
	merge(other : Supplier<CompileResult<T>>, merger : BiFunction<T, T, T>) : CompileResult<T> {return 0;;}
	merge(merger : BiFunction<T, T, T>, tuple : Tuple2<T, T>) : T {break;break;return 0;;}
}
