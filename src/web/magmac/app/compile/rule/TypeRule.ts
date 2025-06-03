export class TypeRule {
	private createError( context : Context,  err : CompileError) : CompileError {return new ImmutableCompileError( "Cannot use type '" + this.type + "'", context, Lists.of( err));;}
	public lex( input : String) : CompileResult<Node> {return this.childRule.lex( input).mapValue( 0).mapErr( 0);;}
	public generate( node : Node) : CompileResult<String> {if(true){ return this.childRule.generate( node).mapErr( 0);;}return CompileErrors.createNodeError( "Type '" + this.type + "' not present", node);;}
}
