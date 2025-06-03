export class LexingStage<T> {
	public getUnitSetApplicationErrorResult( sources1 : Sources) : Result<UnitSet<T>, ApplicationError> {return sources1.readAll( ).mapErr( ThrowableError.new).mapErr( ApplicationError.new).flatMapValue( this.getUnitSetApplicationErrorResult);;}
	private getUnitSetApplicationErrorResult( units : UnitSet<String>) : Result<UnitSet<T>, ApplicationError> {return this.lexer.apply( units).flatMapValue( 0).toResult( ).mapErr( ApplicationError.new);;}
}
