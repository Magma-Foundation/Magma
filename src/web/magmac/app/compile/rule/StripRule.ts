export class StripRule {
	 StripRule( rule : Rule) : public {this( "", rule, "");;}
	public lex( input : String) : CompileResult<Node> {return this.rule.lex( input.strip( ));;}
	public generate( node : Node) : CompileResult<String> { let before : var=node.findString( this.beforeKey).orElse( ""); let after : var=node.findString( this.afterKey).orElse( "");return this.rule.generate( node).mapValue( 0);;}
}
