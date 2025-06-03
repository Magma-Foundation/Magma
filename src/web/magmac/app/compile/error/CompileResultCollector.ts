export class CompileResultCollector<T,  C> {
	public createInitial() : CompileResult<C> {return CompileResults.fromResult( new Ok<>( this.joiner.createInitial( )));;}
	public fold( maybeCurrent : CompileResult<C>,  maybeElement : CompileResult<T>) : CompileResult<C> {return maybeCurrent.flatMapValue( 0);;}
}
