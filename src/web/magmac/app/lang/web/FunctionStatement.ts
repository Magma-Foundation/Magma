export class FunctionStatement {
	 FunctionStatement( child : Value) : public {super( child);;}
	public serialize() : Node {return new MapNode( "statement").withNodeSerialized( "child", this.child);;}
}
