import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { ExactRule } from "../../../../magmac/app/compile/rule/ExactRule";
import { OrRule } from "../../../../magmac/app/compile/rule/OrRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
import { Deserializers } from "../../../../magmac/app/lang/Deserializers";
import { JavaDeserializers } from "../../../../magmac/app/lang/java/JavaDeserializers";
import { JavaRules } from "../../../../magmac/app/lang/JavaRules";
import { JavaAssignmentNode } from "../../../../magmac/app/lang/java/JavaAssignmentNode";
import { JavaFunctionSegmentValue } from "../../../../magmac/app/lang/java/JavaFunctionSegmentValue";
import { JavaPost } from "../../../../magmac/app/lang/java/JavaPost";
export class FunctionSegmentValues {
	deserialize(node : Node) : CompileResult<JavaFunctionSegmentValue> {return 0.orError( 0, 0, 0.of( 0.wrap( 0.deserializeReturn), 0.wrap( 0.deserialize), 0.wrap( 0.deserializeInvocation), 0.wrap( 0), 0.wrap( 0), 0.wrap( 0.deserializeBreak), 0.wrap( 0.deserializeContinue), 0.wrap( 0.deserializeYield)));;}
	createFunctionSegmentValueRule(value : Rule, definition : Rule) : Rule {return new OrRule( 0.of( 0.createInvokableRule( 0), 0.createAssignmentRule( 0, 0), 0.createReturnRule( 0), 0.createYieldRule( 0), 0.createPostRule( 0, 0, 0), 0.createPostRule( 0, 0, 0), new TypeRule( 0, new ExactRule( 0)), new TypeRule( 0, new ExactRule( 0))));;}
}
