import { Option } from "../../../../magmac/api/Option";
import { List } from "../../../../magmac/api/collect/list/List";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Destructors } from "../../../../magmac/app/lang/Destructors";
import { JavaFunctionSegment } from "../../../../magmac/app/lang/java/JavaFunctionSegment";
import { TypescriptValue } from "../../../../magmac/app/lang/web/TypescriptValue";
export class SwitchNode {
	deserialize(node : Node) : Option<CompileResult<SwitchNode>> {return 0;;}
	serialize() : Node {return 0;;}
}
