export interface Result<T,  X> {
	 mapValue( mapper : Function<T, R>) : Result<R, X>;
	 and( supplier : Supplier<Result<R, X>>) : Result<Tuple2<T, R>, X>;
	 match( whenOk : Function<T, R>,  whenErr : Function<X, R>) : R;
	 flatMapValue( mapper : Function<T, Result<R, X>>) : Result<R, X>;
	 mapErr( mapper : Function<X, R>) : Result<T, R>;
}
