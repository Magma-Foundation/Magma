export class ResultCollector<T,  C,  X> {
	public createInitial() : Result<C, X> {return new Ok<>( this.collector.createInitial( ));;}
	public fold( currentResult : Result<C, X>,  element : Result<T, X>) : Result<C, X> {return currentResult.and( ( )->element).mapValue( 0);;}
}
