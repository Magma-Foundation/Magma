import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
export class NodeRule {
	lex(input : String) : CompileResult<Node>;
	generate(node : Node) : CompileResult<String>;
}
