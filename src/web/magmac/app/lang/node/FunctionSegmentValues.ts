import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { ExactRule } from "../../../../magmac/app/compile/rule/ExactRule";
import { OrRule } from "../../../../magmac/app/compile/rule/OrRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
import { Deserializers } from "../../../../magmac/app/lang/Deserializers";
import { JavaDeserializers } from "../../../../magmac/app/lang/JavaDeserializers";
import { JavaRules } from "../../../../magmac/app/lang/JavaRules";
import { JavaAssignmentNode } from "../../../../magmac/app/lang/java/JavaAssignmentNode";
import { JavaFunctionSegmentValue } from "../../../../magmac/app/lang/java/JavaFunctionSegmentValue";
import { JavaPost } from "../../../../magmac/app/lang/java/JavaPost";
export class FunctionSegmentValues {
	deserialize(node : Node) : CompileResult<JavaFunctionSegmentValue> {return 0;;}
	createFunctionSegmentValueRule(value : Rule, definition : Rule) : Rule {return 0;;}
}
