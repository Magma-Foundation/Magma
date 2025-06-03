import { Option } from "../../../../magmac/api/Option";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Destructors } from "../../../../magmac/app/lang/Destructors";
import { JavaFunctionSegmentValue } from "../../../../magmac/app/lang/java/JavaFunctionSegmentValue";
import { Lang } from "../../../../magmac/app/lang/java/Lang";
export class Continue {
	deserialize(node : Node) : Option<CompileResult<Continue>> {;;}
	serialize() : Node {;;}
}
