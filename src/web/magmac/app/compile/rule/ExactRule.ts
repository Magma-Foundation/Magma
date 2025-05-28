import { Ok } from "../../../../magmac/api/result/Ok";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { InlineCompileResult } from "../../../../magmac/app/compile/error/InlineCompileResult";
import { CompileErrors } from "../../../../magmac/app/compile/error/error/CompileErrors";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
export class ExactRule {
	lex(input : String) : CompileResult<Node> {if(input.equals( this.value)){ return InlineCompileResult.fromResult( new Ok<>( new MapNode( )));}return CompileErrors.createStringError( "Slice '" + this.value + "' not present", input);}
	generate(node : Node) : CompileResult<String> {return InlineCompileResult.fromResult( new Ok<>( this.value));}
}
