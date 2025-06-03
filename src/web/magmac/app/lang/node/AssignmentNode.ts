import { Option } from "../../../../magmac/api/Option";
import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { LocatingRule } from "../../../../magmac/app/compile/rule/LocatingRule";
import { NodeRule } from "../../../../magmac/app/compile/rule/NodeRule";
import { OrRule } from "../../../../magmac/app/compile/rule/OrRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
import { Destructors } from "../../../../magmac/app/lang/Destructors";
import { JavaFunctionSegmentValue } from "../../../../magmac/app/lang/java/JavaFunctionSegmentValue";
import { Lang } from "../../../../magmac/app/lang/java/Lang";
export class AssignmentNode {
	deserialize(node : Node) : Option<CompileResult<AssignmentNode>> {;;}
	createAssignmentRule(definition : Rule, value : Rule) : Rule {;;;;}
	serialize() : Node {;;}
}
