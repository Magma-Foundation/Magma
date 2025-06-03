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
import { JavaFunctionSegmentValue } from "../../../../magmac/app/lang/java/JavaFunctionSegmentValue";
import { JavaPost } from "../../../../magmac/app/lang/java/JavaPost";
export class FunctionSegmentValues {
	public static deserialize( node : Node) : CompileResult<JavaFunctionSegmentValue> {return Deserializers.orError( "function-segment-value", node, Lists.of( Deserializers.wrap( JavaDeserializers.deserializeReturn), Deserializers.wrap( JavaDeserializers.deserializeAssignment), Deserializers.wrap( JavaDeserializers.deserializeInvocation), Deserializers.wrap( 0), Deserializers.wrap( 0), Deserializers.wrap( JavaDeserializers.deserializeBreak), Deserializers.wrap( JavaDeserializers.deserializeContinue), Deserializers.wrap( JavaDeserializers.deserializeYield)));;}
	public static createFunctionSegmentValueRule( value : Rule,  definition : Rule) : Rule {return new OrRule( Lists.of( JavaRules.createInvokableRule( value), JavaRules.createAssignmentRule( definition, value), JavaRules.createReturnRule( value), JavaRules.createYieldRule( value), JavaPost.createPostRule( "post-increment", "++", value), JavaPost.createPostRule( "post-decrement", "--", value), new TypeRule( "break", new ExactRule( "break")), new TypeRule( "continue", new ExactRule( "continue"))));;}
}
