import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileErrors } from "../../../../magmac/app/compile/error/error/CompileErrors";
import { Node } from "../../../../magmac/app/compile/node/Node";
export class PrefixRule {
	lex(input : String) : CompileResult<Node> {
		if(!input.startsWith( this.prefix( ))){ 
		return CompileErrors.createStringError( "Prefix '" + this.prefix + "' not present", input);}
		 String sliced=input.substring( this.prefix.length( ));
		return this.childRule.lex( sliced);
	}
	generate(node : Node) : CompileResult<String> {
		return this.childRule.generate( node).mapValue( (value : String) => this.prefix+value);
	}
}
