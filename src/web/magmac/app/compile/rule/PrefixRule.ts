import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileErrors } from "../../../../magmac/app/compile/error/error/CompileErrors";
import { Node } from "../../../../magmac/app/compile/node/Node";
export class PrefixRule {
	lex(input : String) : CompileResult<Node> {if(true){ return CompileErrors.createStringError( "Prefix '" + this.prefix + "' not present", input);;}sliced : String=input.substring( this.prefix.length( ));return this.childRule.lex( sliced);;}
	generate(node : Node) : CompileResult<String> {return this.childRule.generate( node).mapValue( 0);;}
}
