import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { LocatingRule } from "../../../../magmac/app/compile/rule/LocatingRule";
import { NodeRule } from "../../../../magmac/app/compile/rule/NodeRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
import { LazyRule } from "../../../../magmac/app/lang/LazyRule";
import { TypescriptValue } from "../../../../magmac/app/lang/web/TypescriptValue";
export class Operation {
	createOperationRule(operator : Operator, value : LazyRule) : Rule {return 0;;}
	serialize() : Node {return 0;;}
}
