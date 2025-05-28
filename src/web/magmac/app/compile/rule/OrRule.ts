import { Iter } from "../../../../magmac/api/iter/Iter";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Context } from "../../../../magmac/app/compile/error/context/Context";
import { NodeContext } from "../../../../magmac/app/compile/error/context/NodeContext";
import { StringContext } from "../../../../magmac/app/compile/error/context/StringContext";
import { CompileError } from "../../../../magmac/app/compile/error/error/CompileError";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { List } from "../../../../magmac/api/collect/list/List";
import { Function } from "../../../../java/util/function/Function";
export class OrRule {
	foldElement(state : OrState<T>, rule : Rule, mapper : Function<Rule, CompileResult<T>>) : OrState<T> {
		return mapper.apply( rule).match( (value : T) => state.withValue( value), (error : CompileError) => state.withError( error));
	}
	foldAll(mapper : Function<Rule, CompileResult<T>>, context : Context) : CompileResult<T> {
		ruleIter : Iter<Rule>=this.rules.iter( );
		initial : OrState<T>=new OrState<T>( );
		return ruleIter.fold( initial, (state : OrState<T>, rule : Rule) => OrRule.foldElement( state, rule, mapper)).toResult( context);
	}
	lex(input : String) : CompileResult<Node> {
		return this.foldAll( (rule1 : Rule) => rule1.lex( input), new StringContext( input));
	}
	generate(node : Node) : CompileResult<String> {
		return this.foldAll( (rule1 : Rule) => rule1.generate( node), new NodeContext( node));
	}
}
