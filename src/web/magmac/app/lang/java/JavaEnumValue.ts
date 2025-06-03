export class JavaEnumValue {
	public static deserialize( node : Node) : CompileResult<JavaEnumValue> {return Destructors.destruct( node).withString( "name").withNodeListOptionally( "arguments", JavaDeserializers.deserializeValueOrError).complete( 0);;}
	static createEnumValueRule( value : Rule) : Rule { let name : Rule=new StripRule( FilterRule.Symbol( new StringRule( "name"))); let rule : Rule=new SuffixRule( LocatingRule.First( name, "(", JavaRules.createArgumentsRule( value)), ")");return new StripRule( new OrRule( Lists.of( CommonRules.createSymbolRule( "name"), rule)));;}
}
