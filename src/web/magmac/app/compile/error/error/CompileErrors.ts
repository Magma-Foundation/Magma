export class CompileErrors {
	public static createNodeError( message : String,  node : Node) : CompileResult<T> {return CompileResults.fromResult( new Err<>( new ImmutableCompileError( message, new NodeContext( node))));;}
	public static createStringError( message : String,  context : String) : CompileResult<T> {return CompileResults.fromResult( new Err<>( new ImmutableCompileError( message, new StringContext( context))));;}
}
