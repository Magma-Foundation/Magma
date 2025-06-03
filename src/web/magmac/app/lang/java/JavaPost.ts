import { NodeRule } from "../../../../magmac/app/compile/rule/NodeRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { StripRule } from "../../../../magmac/app/compile/rule/StripRule";
import { SuffixRule } from "../../../../magmac/app/compile/rule/SuffixRule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
import { Post } from "../../../../magmac/app/lang/node/Post";
import { PostVariant } from "../../../../magmac/app/lang/node/PostVariant";
export class JavaPost {
	JavaPost(variant : PostVariant, value : JavaLang.Value) : public {super( variant, value);;}
	createPostRule(type : String, suffix : String, value : Rule) : Rule {return new TypeRule( type, new StripRule( new SuffixRule( new NodeRule( "child", value), suffix)));;}
	variant() : PostVariant {return variant;;}
	value() : JavaLang.Value {return value;;}
}
