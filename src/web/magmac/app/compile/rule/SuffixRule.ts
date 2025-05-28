import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileErrors } from "../../../../magmac/app/compile/error/error/CompileErrors";
import { Node } from "../../../../magmac/app/compile/node/Node";
export class SuffixRule {
	lex(input : String) : CompileResult<Node> {
		if(!input.endsWith( this.suffix( ))){ 
		return CompileErrors.createStringError( "Suffix '" + this.suffix + "' not present", input);}
		 String slice=input.substring( 0, input.length( )-this.suffix.length( ));
		return this.childRule.lex( slice);
	}
	generate(node : Node) : CompileResult<String> {
		return this.childRule.generate( node).mapValue( (String result) ->result+this.suffix);
	}
}
