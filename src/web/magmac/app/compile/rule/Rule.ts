import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
export interface Rule {
	lex : CompileResult<Node> {
	}
	generate : CompileResult<String> {
	}
}
