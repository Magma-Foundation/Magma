import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Serializable } from "../../../../magmac/app/lang/Serializable";
export class Symbol {
	 Symbol( value : String) : public {this.value=value;;}
	public value() : String {return this.value;;}
	public serialize() : Node {return new MapNode( "symbol").withString( "value", this.value);;}
}
