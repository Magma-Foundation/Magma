export interface JavaMethodHeader {
	static deserializeError( node : Node) : CompileResult<JavaMethodHeader> {return Deserializers.orError( "header", node, Lists.of( Deserializers.wrap( JavaDeserializers.deserializeTypedDefinition), Deserializers.wrap( JavaConstructor.deserialize)));;}
}
