export class JavaEnumValues {
	public static createEnumValuesRule( value : Rule) : TypeRule { let enumValue : var=JavaEnumValue.createEnumValueRule( value); let enumValues : var=NodeListRule.createNodeListRule( "children", new ValueFolder( ), enumValue); let withEnd : Rule=new StripRule( new SuffixRule( enumValues, ";"));return new TypeRule( "enum-values", new OrRule( Lists.of( withEnd, enumValues)));;}
	public static deserialize( node : Node) : Option<CompileResult<JavaStructureMember>> {return Destructors.destructWithType( "enum-values", node).map( 0);;}
}
