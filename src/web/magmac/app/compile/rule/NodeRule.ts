import { Ok } from "../../../../magmac/api/result/Ok";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileResults } from "../../../../magmac/app/compile/error/CompileResults";
import { CompileErrors } from "../../../../magmac/app/compile/error/error/CompileErrors";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
export class NodeRule {
	private static findNode( node : Node,  key : String) : CompileResult<Node> {
		return node.findNode( key).map( ( node1 : Node) => CompileResults.fromResult( new Ok<>( node1))).orElseGet( ( )->CompileErrors.createNodeError( "Node '" + key + "' not present", node));
	}
	public lex( input : String) : CompileResult<Node> {
		return this.childRule.lex( input).mapValue( ( lexed : Node) => new MapNode( ).withNode( this.key, lexed));
	}
	public generate( node : Node) : CompileResult<String> {
		return NodeRule.findNode( node, this.key).flatMapValue( ( child : Node) => this.childRule.generate( child));
	}
}
