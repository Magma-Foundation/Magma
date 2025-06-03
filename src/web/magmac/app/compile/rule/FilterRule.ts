export class FilterRule {
	 FilterRule( filter : Filter,  childRule : Rule) : private {this.childRule=childRule;this.filter=filter;;}
	public static Symbol( childRule : Rule) : Rule {return new FilterRule( new SymbolFilter( ), childRule);;}
	public static Number( childRule : Rule) : Rule {return new FilterRule( new NumberFilter( ), childRule);;}
	public lex( input : String) : CompileResult<Node> {if(true){ return this.childRule.lex( input);;}if(true){ return CompileErrors.createStringError( this.filter.createMessage( ), input);;};}
	public generate( node : Node) : CompileResult<String> {return this.childRule.generate( node);;}
}
