import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileErrors } from "../../../../magmac/app/compile/error/error/CompileErrors";
import { Node } from "../../../../magmac/app/compile/node/Node";
export class SuffixRule {
	lex(input : String) : CompileResult<Node> {if(true){ return CompileErrors.createStringError( "Suffix '" + this.suffix + "' not present", input);;}break;return this.childRule.lex( slice);;}
	generate(node : Node) : CompileResult<String> {return this.childRule.generate( node).mapValue( 0);;}
}
