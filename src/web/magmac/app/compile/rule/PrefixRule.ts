export class PrefixRule {
	public lex( input : String) : CompileResult<Node> {if(true){ return CompileErrors.createStringError( "Prefix '" + this.prefix + "' not present", input);;} let sliced : var=input.substring( this.prefix.length( ));return this.childRule.lex( sliced);;}
	public generate( node : Node) : CompileResult<String> {return this.childRule.generate( node).mapValue( 0);;}
}
