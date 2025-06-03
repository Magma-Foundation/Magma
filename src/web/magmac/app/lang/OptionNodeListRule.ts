import { Lists } from "../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../magmac/app/compile/node/Node";
import { OrRule } from "../../../magmac/app/compile/rule/OrRule";
import { Rule } from "../../../magmac/app/compile/rule/Rule";
export class OptionNodeListRule {
	lex(input : String) : CompileResult<Node> {return new OrRule( 0.of( 0.ifPresent, 0.ifEmpty)).lex( 0);;}
	generate(node : Node) : CompileResult<String> {if(true){ return 0.ifPresent.generate( 0);;}if(true){ return 0.ifEmpty.generate( 0);;};}
}
