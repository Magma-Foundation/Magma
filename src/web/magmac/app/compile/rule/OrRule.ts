import { List } from "../../../../magmac/api/collect/list/List";
import { Iter } from "../../../../magmac/api/iter/Iter";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Context } from "../../../../magmac/app/compile/error/context/Context";
import { NodeContext } from "../../../../magmac/app/compile/error/context/NodeContext";
import { StringContext } from "../../../../magmac/app/compile/error/context/StringContext";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Function } from "../../../../java/util/function/Function";
export class OrRule {
	foldElement(state : OrState<T>, rule : Rule, mapper : Function<Rule, CompileResult<T>>) : OrState<T> {return 0.apply( 0).match( 0.withValue, 0.withError);;}
	foldAll(mapper : Function<Rule, CompileResult<T>>, context : Context) : CompileResult<T> {break;break;return 0.fold( 0, 0).toResult( 0);;}
	lex(input : String) : CompileResult<Node> {return 0.foldAll( 0, new StringContext( 0));;}
	generate(node : Node) : CompileResult<String> {return 0.foldAll( 0, new NodeContext( 0));;}
}
