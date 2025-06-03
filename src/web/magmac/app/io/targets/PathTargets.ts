export class PathTargets {
	public writeAll( outputs : UnitSet<String>) : Option<IOException> {return outputs.iter( ).map( this.write).flatMap( Iters.fromOption).next( );;}
	private write( entry : Unit<String>) : Option<IOException> {return entry.destruct( 0);;}
}
