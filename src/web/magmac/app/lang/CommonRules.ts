export class CommonRules {
	public static createSymbolRule( key : String) : Rule {return new StripRule( FilterRule.Symbol( new StringRule( key)));;}
	public static createSymbolRule() : Rule {return new TypeRule( "symbol", createSymbolRule( "value"));;}
}
