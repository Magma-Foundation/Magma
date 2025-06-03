import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileErrors } from "../../../../magmac/app/compile/error/error/CompileErrors";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Filter } from "../../../../magmac/app/compile/rule/filter/Filter";
import { NumberFilter } from "../../../../magmac/app/compile/rule/filter/NumberFilter";
import { SymbolFilter } from "../../../../magmac/app/compile/rule/filter/SymbolFilter";
export class FilterRule {
	FilterRule(filter : Filter, childRule : Rule) : private {;;;}
	Symbol(childRule : Rule) : Rule {;;}
	Number(childRule : Rule) : Rule {;;}
	lex(input : String) : CompileResult<Node> {;;;}
	generate(node : Node) : CompileResult<String> {;;}
}
