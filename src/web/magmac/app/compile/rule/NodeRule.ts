export class NodeRule {
	public lex( input : String) : CompileResult<Node> {return this.childRule.lex( input).mapValue( 0);;}
	public generate( node : Node) : CompileResult<String> {return node.findNodeOrError( this.key).flatMapValue( this.childRule.generate);;}
}
