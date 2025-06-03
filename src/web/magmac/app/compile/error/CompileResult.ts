export interface CompileResult<T> {
	 merge( other : Supplier<CompileResult<T>>,  merger : BiFunction<T, T, T>) : CompileResult<T>;
	 mapValue( mapper : Function<T, R>) : CompileResult<R>;
	 match( whenOk : Function<T, R>,  whenErr : Function<CompileError, R>) : R;
	 mapErr( mapper : Function<CompileError, CompileError>) : CompileResult<T>;
	 flatMapValue( mapper : Function<T, CompileResult<R>>) : CompileResult<R>;
	 and( supplier : Supplier<CompileResult<R>>) : CompileResult<Tuple2<T, R>>;
	 toResult() : Result<T, CompileError>;
}
