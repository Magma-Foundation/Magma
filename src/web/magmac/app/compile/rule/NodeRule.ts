import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
export class NodeRule {
	public lex( input : String) : CompileResult<Node> {
		return this.childRule.lex( input).mapValue( ( lexed : Node) => new MapNode( ).withNode( this.key, lexed));
	}
	public generate( node : Node) : CompileResult<String> {
		return node.findNodeOrError( this.key).flatMapValue( ( child : Node) => this.childRule.generate( child));
	}
}
