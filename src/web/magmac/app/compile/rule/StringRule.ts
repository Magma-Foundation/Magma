export class StringRule {
	public static findString( node : Node,  key : String) : CompileResult<String> {return node.findString( key).map( 0).orElseGet( ( )->CompileErrors.createNodeError( "String '" + key + "' not present", node));;}
	public lex( input : String) : CompileResult<Node> {return CompileResults.fromResult( new Ok<>( new MapNode( ).withString( this.key, input)));;}
	public generate( node : Node) : CompileResult<String> {return StringRule.findString( node, this.key);;}
}
