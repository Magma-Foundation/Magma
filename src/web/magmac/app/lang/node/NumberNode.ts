import { Option } from "../../../../magmac/api/Option";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { FilterRule } from "../../../../magmac/app/compile/rule/FilterRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { StringRule } from "../../../../magmac/app/compile/rule/StringRule";
import { StripRule } from "../../../../magmac/app/compile/rule/StripRule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
import { Destructors } from "../../../../magmac/app/lang/Destructors";
export class NumberNode {
	deserialize(node : Node) : Option<CompileResult<NumberNode>> {;;}
	createNumberRule() : Rule {;;}
	serialize() : Node {;;}
}
