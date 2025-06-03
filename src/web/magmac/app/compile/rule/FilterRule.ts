import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileErrors } from "../../../../magmac/app/compile/error/error/CompileErrors";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Filter } from "../../../../magmac/app/compile/rule/filter/Filter";
import { NumberFilter } from "../../../../magmac/app/compile/rule/filter/NumberFilter";
import { SymbolFilter } from "../../../../magmac/app/compile/rule/filter/SymbolFilter";
export class FilterRule {
	FilterRule(filter : Filter, childRule : Rule) : private {break;break;;}
	Symbol(childRule : Rule) : Rule {return 0;;}
	Number(childRule : Rule) : Rule {return 0;;}
	lex(input : String) : CompileResult<Node> {if(true){ return 0;;}if(true){ return 0;;};}
	generate(node : Node) : CompileResult<String> {return 0;;}
}
