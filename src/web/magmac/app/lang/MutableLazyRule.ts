import { None } from "../../../magmac/api/None";
import { Option } from "../../../magmac/api/Option";
import { Some } from "../../../magmac/api/Some";
import { CompileResult } from "../../../magmac/app/compile/error/CompileResult";
import { CompileErrors } from "../../../magmac/app/compile/error/error/CompileErrors";
import { Node } from "../../../magmac/app/compile/node/Node";
import { Rule } from "../../../magmac/app/compile/rule/Rule";
export class MutableLazyRule {
	temp : ?;
	lex(input : String) : CompileResult<Node> {
	}
	generate(node : Node) : CompileResult<String> {
	}
	set(rule : Rule) : LazyRule {
	}
}
