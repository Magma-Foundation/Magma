export class ContextRule {
	public lex( input : String) : CompileResult<Node> {return this.rule.lex( input).mapErr( 0);;}
	public generate( node : Node) : CompileResult<String> {return this.rule.generate( node).mapErr( 0);;}
}
