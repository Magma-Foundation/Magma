export class PathSources {
	private static getTuple2IOResult( source : PathSource) : IOResult<Unit<String>> {return source.read( ).mapValue( 0);;}
	public readAll() : IOResult<UnitSet<String>> {return SafeFiles.walk( this.root).flatMapValue( this.apply);;}
	private apply( sources : Iter<Path>) : IOResult<UnitSet<String>> {return new InlineIOResult<>( this.getCollect( sources));;}
	private getCollect( sources : Iter<Path>) : Result<UnitSet<String>, IOException> {return this.getCollected( sources).iter( ).map( PathSources.getTuple2IOResult).map( IOResult.result).collect( new ResultCollector<>( new UnitSetCollector<>( )));;}
	private getCollected( sources : Iter<Path>) : List<PathSource> {return sources.filter( Files.isRegularFile).filter( 0).map( 0).collect( new ListCollector<>( ));;}
}
