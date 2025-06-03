export class MultipleCaseValue {
	public static deserialize( node : Node) : Option<CompileResult<CaseValue>> {return Destructors.destructWithType( "case-multiple", node).map( 0);;}
	public static createRule( segment : Rule) : TypeRule {return new TypeRule( "case-multiple", new StripRule( new PrefixRule( "{", new SuffixRule( NodeListRule.createNodeListRule( "children", new StatementFolder( ), segment), "}"))));;}
}
