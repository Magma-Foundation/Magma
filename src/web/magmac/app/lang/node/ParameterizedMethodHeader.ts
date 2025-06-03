export class ParameterizedMethodHeader<T extends Serializable> {
	public serialize() : Node {return this.header.serialize( ).withNodeListSerialized( "parameters", this.parameters);;}
}
