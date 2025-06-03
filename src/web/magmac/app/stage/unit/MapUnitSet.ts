export class MapUnitSet<T> {
	 MapUnitSet() : public {this( Maps.empty( ));;}
	public iter() : Iter<Unit<T>> {return this.roots.iter( ).map( 0);;}
	public add( element : Unit<T>) : UnitSet<T> {return new MapUnitSet<>( element.destruct( this.roots.put));;}
	public mapValuesToResult( deserializer : Function<T, CompileResult<R>>) : CompileResult<UnitSet<R>> {return this.roots.iter( ).map( 0).collect( new CompileResultCollector<>( new MapCollector<>( ))).mapValue( MapUnitSet.new);;}
	public mapValues( serializer : Function<T, R>) : UnitSet<R> {return new MapUnitSet<>( this.roots.iter( ).map( 0).collect( new MapCollector<>( )));;}
	public iterValues() : Iter<T> {return this.roots.iter( ).map( Tuple2.right);;}
}
