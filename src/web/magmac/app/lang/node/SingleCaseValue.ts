export class SingleCaseValue {
	public static deserialize( node : Node) : Option<CompileResult<SingleCaseValue>> {return Destructors.destructWithType( "case-single", node).map( 0);;}
	public static createRule( value : Rule) : TypeRule {return new TypeRule( "case-single", new NodeRule( "value", new StripRule( new SuffixRule( value, ";"))));;}
}
