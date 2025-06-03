import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileErrors } from "../../../../magmac/app/compile/error/error/CompileErrors";
import { Node } from "../../../../magmac/app/compile/node/Node";
export class SuffixRule {
	public lex( input : String) : CompileResult<Node> {if(true){ return CompileErrors.createStringError( "Suffix '" + this.suffix + "' not present", input);;} let slice : var=input.substring( 0, input.length( )-this.suffix.length( ));return this.childRule.lex( slice);;}
	public generate( node : Node) : CompileResult<String> {return this.childRule.generate( node).mapValue( 0);;}
}
