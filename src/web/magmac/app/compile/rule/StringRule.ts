import { Ok } from "../../../../magmac/api/result/Ok";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileResults } from "../../../../magmac/app/compile/error/CompileResults";
import { CompileErrors } from "../../../../magmac/app/compile/error/error/CompileErrors";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
export class StringRule {
	findString(node : Node, key : String) : CompileResult<String> {return 0;;}
	lex(input : String) : CompileResult<Node> {return 0;;}
	generate(node : Node) : CompileResult<String> {return 0;;}
}
