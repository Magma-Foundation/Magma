import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { ExactRule } from "../../../../magmac/app/compile/rule/ExactRule";
import { OrRule } from "../../../../magmac/app/compile/rule/OrRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { StripRule } from "../../../../magmac/app/compile/rule/StripRule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
import { Deserializers } from "../../../../magmac/app/lang/Deserializers";
import { JavaDeserializers } from "../../../../magmac/app/lang/java/JavaDeserializers";
import { JavaRules } from "../../../../magmac/app/lang/JavaRules";
import { LazyRule } from "../../../../magmac/app/lang/LazyRule";
import { JavaFunctionSegment } from "../../../../magmac/app/lang/java/JavaFunctionSegment";
export class FunctionSegments {
	deserialize(node : Node) : CompileResult<JavaFunctionSegment> {return Deserializers.orError( "function-segment", node, Lists.of( Deserializers.wrap( JavaDeserializers.deserializeWhitespace), Deserializers.wrap( JavaDeserializers.deserializeFunctionStatement), Deserializers.wrap( JavaDeserializers.deserializeBlock), Deserializers.wrap( JavaDeserializers.deserializeReturn), Deserializers.wrap( JavaDeserializers.deserializeCase)));;}
	initFunctionSegmentRule(functionSegmentRule : LazyRule, value : Rule, definition : Rule) : Rule {break;break;return functionSegmentRule.set( new StripRule( "before", rule, ""));;}
}
