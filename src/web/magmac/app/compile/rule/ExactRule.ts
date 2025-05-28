import { Ok } from "../../../../magmac/api/result/Ok";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileResults } from "../../../../magmac/app/compile/error/CompileResults";
import { CompileErrors } from "../../../../magmac/app/compile/error/error/CompileErrors";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
export class ExactRule {
	public lex( input : String) : CompileResult<Node> {
		if(input.equals( this.value)){ 
		return CompileResults.fromResult( new Ok<>( new MapNode( )));}
		return CompileErrors.createStringError( "Slice '" + this.value + "' not present", input);
	}
	public generate( node : Node) : CompileResult<String> {
		return CompileResults.fromResult( new Ok<>( this.value));
	}
}
