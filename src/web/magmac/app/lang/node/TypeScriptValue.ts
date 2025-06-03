import { Node } from "../../../../magmac/app/compile/node/Node";
import { Serializable } from "../../../../magmac/app/lang/Serializable";
export interface TypeScriptValue {
	serialize() : Node;
}
