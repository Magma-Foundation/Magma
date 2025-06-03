import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Destructors } from "../../../../magmac/app/lang/Destructors";
export class Segment {
	deserialize(node : Node) : CompileResult<Segment> {return 0.destruct( 0).withString( 0).complete( 0.new);;}
	serialize() : Node {return new MapNode( 0).withString( 0, 0.value);;}
}
