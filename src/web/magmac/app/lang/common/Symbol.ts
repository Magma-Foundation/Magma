import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Serializable } from "../../../../magmac/app/lang/Serializable";
export class Symbol {
	Symbol(value : String) : public {break;;}
	value() : String {return 0.value;;}
	serialize() : Node {return new MapNode( 0).withString( 0, 0.value);;}
}
