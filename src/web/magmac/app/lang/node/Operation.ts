import { LocatingRule } from "../../../../magmac/app/compile/rule/LocatingRule";
import { NodeRule } from "../../../../magmac/app/compile/rule/NodeRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
export class Operation {
	createOperationRule(value : Rule, type : String, infix : String) : Rule;
}
