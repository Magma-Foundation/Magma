export class InlineCompileResult<T> {
	public mapValue( mapper : Function<T, R>) : CompileResult<R> {return CompileResults.fromResult( this.toResult.mapValue( mapper));;}
	public match( whenOk : Function<T, R>,  whenErr : Function<CompileError, R>) : R {return this.toResult.match( whenOk, whenErr);;}
	public mapErr( mapper : Function<CompileError, CompileError>) : CompileResult<T> {return CompileResults.fromResult( this.toResult.mapErr( mapper));;}
	public flatMapValue( mapper : Function<T, CompileResult<R>>) : CompileResult<R> {return CompileResults.fromResult( this.toResult.flatMapValue( 0));;}
	public and( supplier : Supplier<CompileResult<R>>) : CompileResult<Tuple2<T, R>> {return CompileResults.fromResult( this.toResult.and( ( )->supplier.get( ).toResult( )));;}
	public merge( other : Supplier<CompileResult<T>>,  merger : BiFunction<T, T, T>) : CompileResult<T> {return this.and( other).mapValue( 0);;}
	private merge( merger : BiFunction<T, T, T>,  tuple : Tuple2<T, T>) : T { let left0 : var=tuple.left( ); let right0 : var=tuple.right( );return merger.apply( left0, right0);;}
}
