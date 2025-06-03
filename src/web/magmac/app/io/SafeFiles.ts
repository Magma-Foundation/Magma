export class SafeFiles {
	public static writeString( target : Path,  output : String) : Option<IOException> {if(true){ Files.writeString( target, output);return new None<>( );;}if(true){ return new Some<>( e);;};}
	public static walk( sourceDirectory : Path) : IOResult<Iter<Path>> {if(true){  let stream : var=Files.walk( sourceDirectory);return new InlineIOResult<>( new Ok<>( new JVMList<>( stream.collect( Collectors.toList( ))).iter( )));;}if(true){ return new InlineIOResult<>( new Err<>( e));;};}
	public static readString( source : Path) : IOResult<String> {if(true){ return new InlineIOResult<>( new Ok<>( Files.readString( source)));;}if(true){ return new InlineIOResult<>( new Err<>( e));;};}
	public static createDirectories( targetParent : Path) : Option<IOException> {if(true){ Files.createDirectories( targetParent);return new None<>( );;}if(true){ return new Some<>( e);;};}
}
