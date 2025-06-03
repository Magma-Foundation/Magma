export class CaseDefinition {
	public static deserialize( node : Node) : CompileResult<CaseDefinition> {return Destructors.destruct( node).withString( "name").withNodeOptionally( "type", JavaDeserializers.deserializeType).complete( 0);;}
}
