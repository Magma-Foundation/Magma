import { Option } from "../../../../magmac/api/Option";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
export class JavaDefinition {
	deserialize(node : Node) : CompileResult<JavaDefinition>;
	deserializeTyped(node : Node) : Option<CompileResult<JavaDefinition>>;
}
