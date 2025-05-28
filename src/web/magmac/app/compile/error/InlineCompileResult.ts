import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { Ok } from "../../../../magmac/api/result/Ok";
import { Result } from "../../../../magmac/api/result/Result";
import { CompileError } from "../../../../magmac/app/compile/error/error/CompileError";
import { BiFunction } from "../../../../java/util/function/BiFunction";
import { Function } from "../../../../java/util/function/Function";
import { Supplier } from "../../../../java/util/function/Supplier";
export class InlineCompileResult {private final result() : Result<T, CompileError>;
	 InlineCompileResult( result() : Result<T, CompileError>) : private {
		this.result=result;
	}
	public static fromResult( result() : Result<T, CompileError>) : CompileResult<T> {
		return new InlineCompileResult<T>( result);
	}
	public static fromOk( value() : T) : CompileResult<T> {
		return InlineCompileResult.fromResult( new Ok<>( value));
	}
	public mapValue( mapper() : Function<T, R>) : CompileResult<R> {
		return InlineCompileResult.fromResult( this.result.mapValue( mapper));
	}
	public match( whenOk() : Function<T, R>,  whenErr() : Function<CompileError, R>) : R {
		return this.result.match( whenOk, whenErr);
	}
	public mapErr( mapper() : Function<CompileError, CompileError>) : CompileResult<T> {
		return InlineCompileResult.fromResult( this.result.mapErr( mapper));
	}
	public flatMapValue( mapper() : Function<T, CompileResult<R>>) : CompileResult<R> {
		return InlineCompileResult.fromResult( this.result.flatMapValue( ( t() : T) => mapper.apply( t).result( )));
	}
	public and( supplier() : Supplier<CompileResult<R>>) : CompileResult<Tuple2<T, R>> {
		return InlineCompileResult.fromResult( this.result.and( ( )->supplier.get( ).result( )));
	}
	public result() : Result<T, CompileError> {
		return this.result;
	}
	public merge( other() : Supplier<CompileResult<T>>,  merger() : BiFunction<T, T, T>) : CompileResult<T> {
		return this.and( other).mapValue( ( tuple() : Tuple2<T, T>) => this.merge( merger, tuple));
	}
	private merge( merger() : BiFunction<T, T, T>,  tuple() : Tuple2<T, T>) : T {
		 left0() : T=tuple.left( );
		 right0() : T=tuple.right( );
		return merger.apply( left0, right0);
	}
}
