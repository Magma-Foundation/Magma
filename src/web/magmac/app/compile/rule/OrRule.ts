import { List } from "../../../../magmac/api/collect/list/List";
import { Iter } from "../../../../magmac/api/iter/Iter";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Context } from "../../../../magmac/app/compile/error/context/Context";
import { NodeContext } from "../../../../magmac/app/compile/error/context/NodeContext";
import { StringContext } from "../../../../magmac/app/compile/error/context/StringContext";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Function } from "../../../../java/util/function/Function";
export class OrRule {
	foldElement(state : OrState<T>, rule : Rule, mapper : Function<Rule, CompileResult<T>>) : OrState<T> {return mapper.apply( rule).match( state.withValue, state.withError);;}
	foldAll(mapper : Function<Rule, CompileResult<T>>, context : Context) : CompileResult<T> {ruleIter : Iter<Rule>=this.rules.iter( );initial : OrState<T>=new OrState<T>( );return ruleIter.fold( initial, 0).toResult( context);;}
	lex(input : String) : CompileResult<Node> {return this.foldAll( 0, new StringContext( input));;}
	generate(node : Node) : CompileResult<String> {return this.foldAll( 0, new NodeContext( node));;}
}
