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
import { JavaCaseNode } from "../../../../magmac/app/lang/java/JavaCaseNode";
import { JavaFunctionSegment } from "../../../../magmac/app/lang/java/JavaFunctionSegment";
export class FunctionSegments {
	deserialize(node : Node) : CompileResult<JavaFunctionSegment> {return 0.orError( 0, 0, 0.of( 0.wrap( 0.deserializeWhitespace), 0.wrap( 0.deserializeFunctionStatement), 0.wrap( 0.deserializeBlock), 0.wrap( 0.deserializeReturn), 0.wrap( 0.deserialize)));;}
	initFunctionSegmentRule(functionSegmentRule : LazyRule, value : Rule, definition : Rule) : Rule {break;break;return 0.set( new StripRule( 0, 0, 0));;}
}
