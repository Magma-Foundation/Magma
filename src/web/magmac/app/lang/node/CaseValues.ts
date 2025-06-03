export class CaseValues {
	public static deserializeOrError( node : Node) : CompileResult<CaseValue> {return Deserializers.orError( "case-value", node, Lists.of( Deserializers.wrap( SingleCaseValue.deserialize), Deserializers.wrap( MultipleCaseValue.deserialize)));;}
}
