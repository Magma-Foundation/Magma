import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { FunctionStatement } from "../../../../magmac/app/lang/common/FunctionStatement";
export class TypescriptFunctionStatement {
	TypescriptFunctionStatement(child : TypescriptFunctionSegmentValue) : public {0( 0);;}
	serialize() : Node {return new MapNode( "statement").withNodeSerialized( "child", 0.child);;}
}
