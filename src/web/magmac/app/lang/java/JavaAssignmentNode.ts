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
import { Assignables } from "../../../../magmac/app/lang/node/Assignables";
import { StructureStatementValue } from "../../../../magmac/app/lang/node/StructureStatementValue";
import { TypescriptLang } from "../../../../magmac/app/lang/web/TypescriptLang";
export class JavaAssignmentNode {
	deserialize(node : Node) : Option<CompileResult<JavaAssignmentNode>> {return Destructors.destructWithType( "assignment", node).map( 0);;}
	createAssignmentRule(definition : Rule, value : Rule) : Rule {break;break;return new TypeRule( "assignment", LocatingRule.First( before, "=", source));;}
	serialize() : Node {return new MapNode( "assignment");;}
}
