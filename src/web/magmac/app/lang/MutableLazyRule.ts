import { None } from "../../../magmac/api/None";
import { Option } from "../../../magmac/api/Option";
import { Some } from "../../../magmac/api/Some";
import { CompileResult } from "../../../magmac/app/compile/error/CompileResult";
import { CompileErrors } from "../../../magmac/app/compile/error/error/CompileErrors";
import { Node } from "../../../magmac/app/compile/node/Node";
import { Rule } from "../../../magmac/app/compile/rule/Rule";
export class MutableLazyRule {private Option<Rule> maybeRule = None<>() : new;
	public lex( input : String) : CompileResult<Node> {
		return this.maybeRule.map( ( rule : Rule) => rule.lex( input)).orElseGet( ( )->CompileErrors.createStringError( "Rule not set", input));
	}
	public generate( node : Node) : CompileResult<String> {
		return this.maybeRule.map( ( rule : Rule) => rule.generate( node)).orElseGet( ( )->CompileErrors.createNodeError( "Rule not set", node));
	}
	public set( rule : Rule) : LazyRule {
		this.maybeRule=new Some<>( rule);
		return this;
	}
}
