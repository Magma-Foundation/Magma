import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { Ok } from "../../../../magmac/api/result/Ok";
import { Result } from "../../../../magmac/api/result/Result";
import { CompileError } from "../../../../magmac/app/compile/error/error/CompileError";
import { BiFunction } from "../../../../java/util/function/BiFunction";
import { Function } from "../../../../java/util/function/Function";
import { Supplier } from "../../../../java/util/function/Supplier";
export class InlineCompileResult {
	temp : ?;
	InlineCompileResult(result : Result<T, CompileError>) : private {
		this.result=result;
	}
	fromResult(result : Result<T, CompileError>) : CompileResult<T> {
		return new InlineCompileResult<T>( result);
	}
	fromOk(value : T) : CompileResult<T> {
		return InlineCompileResult.fromResult( new Ok<>( value));
	}
	mapValue(mapper : Function<T, R>) : CompileResult<R> {
		return InlineCompileResult.fromResult( this.result.mapValue( mapper));
	}
	match(whenOk : Function<T, R>, whenErr : Function<CompileError, R>) : R {
		return this.result.match( whenOk, whenErr);
	}
	mapErr(mapper : Function<CompileError, CompileError>) : CompileResult<T> {
		return InlineCompileResult.fromResult( this.result.mapErr( mapper));
	}
	flatMapValue(mapper : Function<T, CompileResult<R>>) : CompileResult<R> {
		return InlineCompileResult.fromResult( this.result.flatMapValue( (T t)  => mapper.apply( t).result( )));
	}
	and(supplier : Supplier<CompileResult<R>>) : CompileResult<Tuple2<T, R>> {
		return InlineCompileResult.fromResult( this.result.and( ()  => supplier.get( ).result( )));
	}
	result() : Result<T, CompileError> {
		return this.result;
	}
	merge(other : Supplier<CompileResult<T>>, merger : BiFunction<T, T, T>) : CompileResult<T> {
		return this.and( other).mapValue( (Tuple2<T, T> tuple)  => this.merge( merger, tuple));
	}
	merge(merger : BiFunction<T, T, T>, tuple : Tuple2<T, T>) : T {
		 T left0=tuple.left( );
		 T right0=tuple.right( );
		return merger.apply( left0, right0);
	}
}
