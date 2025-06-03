export class Deserializers {
	public static orError( type : String,  node : Node,  deserializers : List<TypedDeserializer<T>>) : CompileResult<T> {return Deserializers.or( node, deserializers).map( 0).orElseGet( ( )->CompileResults.NodeErr( "Cannot deserialize of type '" + type + "'", node));;}
	private static wrap( type : String,  node : Node,  err : CompileError) : CompileError {return new ImmutableCompileError( "Invalid type '" + type + "'", new NodeContext( node), Lists.of( err));;}
	public static or( node : Node,  deserializers : List<TypedDeserializer<T>>) : Option<CompileResult<T>> {return deserializers.iter( ).map( 0).flatMap( Iters.fromOption).next( );;}
	public static wrap( deserializer : TypedDeserializer<T>) : TypedDeserializer<R> {return 0;;}
}
