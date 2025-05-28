import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
export class StripRule {
	 StripRule( rule : Rule) : public {
		this( "", rule, "");
	}
	public lex( input : String) : CompileResult<Node> {
		return this.rule.lex( input.strip( ));
	}
	public generate( node : Node) : CompileResult<String> {
		 before : String=node.findString( this.beforeKey).orElse( "");
		 after : String=node.findString( this.afterKey).orElse( "");
		return this.rule.generate( node).mapValue( ( result : String) => before+result+after);
	}
}
