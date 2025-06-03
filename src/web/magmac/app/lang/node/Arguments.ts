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
	deserialize(node : Node) : CompileResult<JavaLang.JavaArgument> {return 0.orError( 0, 0, 0.of( 0.wrap( 0.deserializeWhitespace), 0.wrap( 0.deserializeValue)));;}
	createArgumentsRule(value : Rule) : Rule {return 0.Values( 0, new OrRule( 0.of( 0.createWhitespaceRule( ), 0)));;}
}
