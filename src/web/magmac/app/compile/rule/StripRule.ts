import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
export class StripRule {
	StripRule(rule : Rule) : public {this( "", rule, "");;}
	lex(input : String) : CompileResult<Node> {return this.rule.lex( input.strip( ));;}
	generate(node : Node) : CompileResult<String> {before : var=node.findString( this.beforeKey).orElse( "");after : var=node.findString( this.afterKey).orElse( "");return this.rule.generate( node).mapValue( 0);;}
}
