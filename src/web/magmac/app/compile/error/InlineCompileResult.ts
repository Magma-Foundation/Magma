import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { Result } from "../../../../magmac/api/result/Result";
import { CompileError } from "../../../../magmac/app/compile/error/error/CompileError";
import { BiFunction } from "../../../../java/util/function/BiFunction";
import { Function } from "../../../../java/util/function/Function";
import { Supplier } from "../../../../java/util/function/Supplier";
export class InlineCompileResult<T> {
	mapValue(mapper : Function<T, R>) : CompileResult<R> {return CompileResults.fromResult( this.toResult.mapValue( mapper));;}
	match(whenOk : Function<T, R>, whenErr : Function<CompileError, R>) : R {return this.toResult.match( whenOk, whenErr);;}
	mapErr(mapper : Function<CompileError, CompileError>) : CompileResult<T> {return CompileResults.fromResult( this.toResult.mapErr( mapper));;}
	flatMapValue(mapper : Function<T, CompileResult<R>>) : CompileResult<R> {return CompileResults.fromResult( this.toResult.flatMapValue( 0));;}
	and(supplier : Supplier<CompileResult<R>>) : CompileResult<Tuple2<T, R>> {return CompileResults.fromResult( this.toResult.and( 0));;}
	merge(other : Supplier<CompileResult<T>>, merger : BiFunction<T, T, T>) : CompileResult<T> {return this.and( other).mapValue( 0);;}
	merge(merger : BiFunction<T, T, T>, tuple : Tuple2<T, T>) : T {break;break;return merger.apply( left0, right0);;}
}
