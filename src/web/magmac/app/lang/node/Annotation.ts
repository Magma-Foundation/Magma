import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Deserializers } from "../../../../magmac/app/lang/Deserializers";
export class Annotation {
	deserialize(node : Node) : CompileResult<Annotation>;
}
