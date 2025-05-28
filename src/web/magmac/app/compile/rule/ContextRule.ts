import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { NodeContext } from "../../../../magmac/app/compile/error/context/NodeContext";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { StringContext } from "../../../../magmac/app/compile/error/context/StringContext";
import { ImmutableCompileError } from "../../../../magmac/app/error/ImmutableCompileError";
export class ContextRule {
	lex : CompileResult<Node> {
	}
	generate : CompileResult<String> {
	}
}
