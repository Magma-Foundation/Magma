import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Serializable } from "../../../../magmac/app/lang/Serializable";
export class Symbol {
	Symbol(value : String) : public {this.value=value;;}
	value() : String {return this.value;;}
	serialize() : Node {return new MapNode( "symbol").withString( "value", this.value);;}
}
