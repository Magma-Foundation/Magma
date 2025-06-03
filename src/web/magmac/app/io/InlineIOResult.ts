export class InlineIOResult<T> {
	public mapValue( mapper : Function<T, R>) : IOResult<R> {return new InlineIOResult<>( this.result.mapValue( mapper));;}
	public flatMapValue( mapper : Function<T, IOResult<R>>) : IOResult<R> {return new InlineIOResult<>( this.result.flatMapValue( 0));;}
	public mapErr( mapper : Function<IOException, R>) : Result<T, R> {return this.result.mapErr( mapper);;}
}
