export class Segment {
	public static deserialize( node : Node) : CompileResult<Segment> {return Destructors.destruct( node).withString( "value").complete( Segment.new);;}
	public serialize() : Node {return new MapNode( "segment").withString( "value", this.value);;}
}
