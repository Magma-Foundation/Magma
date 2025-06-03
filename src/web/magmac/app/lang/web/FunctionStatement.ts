import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { AbstractFunctionStatement } from "../../../../magmac/app/lang/common/AbstractFunctionStatement";
export class FunctionStatement {
	 FunctionStatement( child : Value) : public {super( child);;}
	public serialize() : Node {return new MapNode( "statement").withNodeSerialized( "child", this.child);;}
}
