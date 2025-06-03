import { NodeRule } from "../../../../magmac/app/compile/rule/NodeRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { StripRule } from "../../../../magmac/app/compile/rule/StripRule";
import { SuffixRule } from "../../../../magmac/app/compile/rule/SuffixRule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
import { JavaFunctionSegmentValue } from "../../../../magmac/app/lang/java/JavaFunctionSegmentValue";
import { Objects } from "../../../../java/util/Objects";
export class JavaPost {
	JavaPost(variant : PostVariant, value : JavaValue) : public {;;}
	createPostRule(type : String, suffix : String, value : Rule) : Rule {;;}
	variant() : PostVariant {;;}
	value() : JavaValue {;;}
}
