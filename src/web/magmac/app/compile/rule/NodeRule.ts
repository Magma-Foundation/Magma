import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
export class NodeRule {
	lex(input : String) : CompileResult<Node> {return this.childRule.lex( input).mapValue( 0);;}
	generate(node : Node) : CompileResult<String> {return node.findNodeOrError( this.key).flatMapValue( this.childRule.generate);;}
}
