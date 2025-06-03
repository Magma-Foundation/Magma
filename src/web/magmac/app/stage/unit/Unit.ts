export interface Unit<T> {
	 destruct( merger : BiFunction<Location, T, R>) : R;
	 mapValue( mapper : Function<T, CompileResult<R>>) : CompileResult<Unit<R>>;
	 display() : String;
}
