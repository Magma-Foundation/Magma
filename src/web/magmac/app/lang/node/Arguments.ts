import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { NodeListRule } from "../../../../magmac/app/compile/rule/NodeListRule";
import { OrRule } from "../../../../magmac/app/compile/rule/OrRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { Deserializers } from "../../../../magmac/app/lang/Deserializers";
import { JavaDeserializers } from "../../../../magmac/app/lang/java/JavaDeserializers";
import { JavaRules } from "../../../../magmac/app/lang/JavaRules";
import { JavaLang } from "../../../../magmac/app/lang/java/JavaLang";
export class Arguments {
	deserialize(node : Node) : CompileResult<JavaLang.JavaArgument> {return Deserializers.orError( "argument", node, Lists.of( Deserializers.wrap( JavaDeserializers.deserializeWhitespace), Deserializers.wrap( JavaDeserializers.deserializeValue)));;}
	createArgumentsRule(value : Rule) : Rule {return NodeListRule.Values( "arguments", new OrRule( Lists.of( JavaRules.createWhitespaceRule( ), value)));;}
}
