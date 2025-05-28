import { Ok } from "../../../../magmac/api/result/Ok";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { InlineCompileResult } from "../../../../magmac/app/compile/error/InlineCompileResult";
import { CompileErrors } from "../../../../magmac/app/compile/error/error/CompileErrors";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
export class NodeRule {
	private findNode( node : Node) : CompileResult<Node> {
		return node.findNode( this.key).map( ( node1 : Node) => InlineCompileResult.fromResult( new Ok<>( node1))).orElseGet( ( )->CompileErrors.createNodeError( "Node '" + this.key + "' not present", node));
	}
	public lex( input : String) : CompileResult<Node> {
		return this.childRule.lex( input).mapValue( ( lexed : Node) => new MapNode( ).withNode( this.key, lexed));
	}
	public generate( node : Node) : CompileResult<String> {
		return this.findNode( node).flatMapValue( ( child : Node) => this.childRule.generate( child));
	}
}
