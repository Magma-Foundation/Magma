import { Iter } from "../../../../magmac/api/iter/Iter";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Context } from "../../../../magmac/app/compile/error/context/Context";
import { NodeContext } from "../../../../magmac/app/compile/error/context/NodeContext";
import { StringContext } from "../../../../magmac/app/compile/error/context/StringContext";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { List } from "../../../../magmac/api/collect/list/List";
import { Function } from "../../../../java/util/function/Function";
export class OrRule {
	foldElement(state : OrState<T>, rule : Rule, mapper : Function<Rule, CompileResult<T>>) : OrState<T> {
	}
	foldAll(mapper : Function<Rule, CompileResult<T>>, context : Context) : CompileResult<T> {
	}
	lex(input : String) : CompileResult<Node> {
	}
	generate(node : Node) : CompileResult<String> {
	}
}
