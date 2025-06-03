import { Option } from "../../../../magmac/api/Option";
import { List } from "../../../../magmac/api/collect/list/List";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Destructors } from "../../../../magmac/app/lang/Destructors";
import { JavaFunctionSegment } from "../../../../magmac/app/lang/java/JavaFunctionSegment";
export class SwitchNode {
	deserialize(node : Node) : Option<CompileResult<SwitchNode>> {break;;}
	serialize() : Node {break;;}
}
