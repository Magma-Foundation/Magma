export class ExactRule {
	public lex( input : String) : CompileResult<Node> {if(true){ return CompileResults.fromResult( new Ok<>( new MapNode( )));;}return CompileErrors.createStringError( "Slice '" + this.value + "' not present", input);;}
	public generate( node : Node) : CompileResult<String> {return CompileResults.fromResult( new Ok<>( this.value));;}
}
