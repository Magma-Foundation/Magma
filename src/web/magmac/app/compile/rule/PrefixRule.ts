import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileErrors } from "../../../../magmac/app/compile/error/error/CompileErrors";
import { Node } from "../../../../magmac/app/compile/node/Node";
export class PrefixRule {
	lex(input : String) : CompileResult<Node> {if(true){ return 0.createStringError( 0, 0);;}break;return 0.childRule.lex( 0);;}
	generate(node : Node) : CompileResult<String> {return 0.childRule.generate( 0).mapValue( 0);;}
}
