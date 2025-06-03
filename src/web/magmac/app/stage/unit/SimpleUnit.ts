export class SimpleUnit<T> {
	 SimpleUnit( location : Location,  value : T) : public {this.location=location;this.value=value;;}
	public destruct( merger : BiFunction<Location, T, R>) : R {return merger.apply( this.location, this.value);;}
	public mapValue( mapper : Function<T, CompileResult<R>>) : CompileResult<Unit<R>> {return mapper.apply( this.value).mapValue( 0);;}
	public display() : String {return this.location.toString( );;}
}
