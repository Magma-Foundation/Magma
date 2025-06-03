import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
export class NodeRule {
	lex(input : String) : CompileResult<Node> {return 0.childRule.lex( 0).mapValue( 0);;}
	generate(node : Node) : CompileResult<String> {return 0.findNodeOrError( 0.key).flatMapValue( 0.childRule.generate);;}
}
