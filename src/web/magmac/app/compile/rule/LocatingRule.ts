export class LocatingRule {
	 LocatingRule( leftRule : Rule,  splitter : Splitter,  rightRule : Rule) : public {this.leftRule=leftRule;this.rightRule=rightRule;this.splitter=splitter;;}
	public static First( leftRule : Rule,  infix : String,  rightRule : Rule) : Rule {return LocatingRule.createLocatingRule( leftRule, infix, rightRule, new FirstLocator( ));;}
	public static Last( leftRule : Rule,  infix : String,  rightRule : Rule) : Rule {return LocatingRule.createLocatingRule( leftRule, infix, rightRule, new LastLocator( ));;}
	private static createLocatingRule( leftRule : Rule,  infix : String,  rightRule : Rule,  locator : Locator) : Rule {return new LocatingRule( leftRule, new LocatingSplitter( infix, locator), rightRule);;}
	public lex( input : String) : CompileResult<Node> {return this.splitter.split( input).map( 0).orElseGet( ( )->CompileErrors.createStringError( this.splitter.createMessage( ), input));;}
	public generate( node : Node) : CompileResult<String> {return this.leftRule.generate( node).merge( ( )->this.rightRule.generate( node), this.splitter.merge);;}
}
