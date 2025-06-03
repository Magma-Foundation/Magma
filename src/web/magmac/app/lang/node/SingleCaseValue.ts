import { Option } from "../../../../magmac/api/Option";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { NodeRule } from "../../../../magmac/app/compile/rule/NodeRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { StripRule } from "../../../../magmac/app/compile/rule/StripRule";
import { SuffixRule } from "../../../../magmac/app/compile/rule/SuffixRule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
import { Destructors } from "../../../../magmac/app/lang/Destructors";
import { JavaDeserializers } from "../../../../magmac/app/lang/java/JavaDeserializers";
import { JavaLang } from "../../../../magmac/app/lang/java/JavaLang";
export class SingleCaseValue {
	public static deserialize( node : Node) : Option<CompileResult<SingleCaseValue>> {return Destructors.destructWithType( "case-single", node).map( 0);;}
	public static createRule( value : Rule) : TypeRule {return new TypeRule( "case-single", new NodeRule( "value", new StripRule( new SuffixRule( value, ";"))));;}
}
