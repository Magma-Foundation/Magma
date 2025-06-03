export interface IOResult<T> {
	 mapValue( mapper : Function<T, R>) : IOResult<R>;
	 flatMapValue( mapper : Function<T, IOResult<R>>) : IOResult<R>;
	 mapErr( mapper : Function<IOException, R>) : Result<T, R>;
	 result() : Result<T, IOException>;
}
