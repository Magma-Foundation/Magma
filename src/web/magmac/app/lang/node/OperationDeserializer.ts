export class OperationDeserializer {
	public deserialize( node : Node) : Option<CompileResult<operation>> {return Destructors.destructWithType( this.operator( ).type( ), node).map( 0);;}
}
