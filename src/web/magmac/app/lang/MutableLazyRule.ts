export class MutableLazyRule {
	public lex( input : String) : CompileResult<Node> {return this.maybeRule.map( 0).orElseGet( ( )->CompileErrors.createStringError( "Rule not set", input));;}
	public generate( node : Node) : CompileResult<String> {return this.maybeRule.map( 0).orElseGet( ( )->CompileErrors.createNodeError( "Rule not set", node));;}
	public set( rule : Rule) : LazyRule {this.maybeRule=new Some<>( rule);return this;;}
}
