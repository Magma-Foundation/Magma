export class CompileResults {
	public static fromResult( result : Result<T, CompileError>) : CompileResult<T> {return new InlineCompileResult<T>( result);;}
	public static Ok( value : T) : CompileResult<T> {return new InlineCompileResult<T>( new Ok<T, CompileError>( value));;}
	public static NodeErr( message : String,  context : Node) : CompileResult<T> {return new InlineCompileResult<>( new Err<>( new ImmutableCompileError( message, new NodeContext( context))));;}
	public static fromErrWithString( message : String,  context : String) : CompileResult<T> {return CompileResults.fromWithContext( message, new StringContext( context));;}
	private static fromWithContext( message : String,  context : Context) : CompileResult<T> {return new InlineCompileResult<>( new Err<>( new ImmutableCompileError( message, context)));;}
}
