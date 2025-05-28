import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
export class StripRule {
	StripRule(rule : Rule) : public {
		this( "", rule, "");
	}
	lex(input : String) : CompileResult<Node> {
		return this.rule.lex( input.strip( ));
	}
	generate(node : Node) : CompileResult<String> {
		 String before=node.findString( this.beforeKey).orElse( "");
		 String after=node.findString( this.afterKey).orElse( "");
		return this.rule.generate( node).mapValue( result  => before+result+after);
	}
}
