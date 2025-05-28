import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
export interface Rule {
	lex(input : String) : CompileResult<Node> {
	}
	generate(node : Node) : CompileResult<String> {
	}
}
