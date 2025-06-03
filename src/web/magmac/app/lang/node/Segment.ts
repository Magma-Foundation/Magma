import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Destructors } from "../../../../magmac/app/lang/Destructors";
export class Segment {
	deserialize(node : Node) : CompileResult<Segment> {return Destructors.destruct( node).withString( "value").complete( Segment.new);;}
	serialize() : Node {return new MapNode( "segment").withString( "value", this.value);;}
}
