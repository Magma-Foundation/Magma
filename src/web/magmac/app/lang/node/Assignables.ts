export class Assignables {
	public static deserializeError( node : Node) : CompileResult<JavaLang.Assignable> {return Deserializers.orError( "assignable", node, Lists.of( Deserializers.wrap( JavaDeserializers.deserializeTypedDefinition), Deserializers.wrap( JavaDeserializers.deserializeValue)));;}
}
