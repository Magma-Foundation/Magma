import { Ok } from "../../../../magmac/api/result/Ok";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { InlineCompileResult } from "../../../../magmac/app/compile/error/InlineCompileResult";
import { CompileErrors } from "../../../../magmac/app/compile/error/error/CompileErrors";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
export class StringRule {
	public lex( input : String) : CompileResult<Node> {
		return InlineCompileResult.fromResult( new Ok<>( new MapNode( ).withString( this.key, input)));
	}
	public generate( node : Node) : CompileResult<String> {
		return node.findString( this.key).map( ( value : String) => InlineCompileResult.fromResult( new Ok<>( value))).orElseGet( ( )->CompileErrors.createNodeError( "String '" + this.key + "' not present", node));
	}
}
