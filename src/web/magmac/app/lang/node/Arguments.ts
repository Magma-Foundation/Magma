import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { NodeListRule } from "../../../../magmac/app/compile/rule/NodeListRule";
import { OrRule } from "../../../../magmac/app/compile/rule/OrRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { Deserializers } from "../../../../magmac/app/lang/Deserializers";
import { Lang } from "../../../../magmac/app/lang/java/Lang";
export class Arguments {
	deserialize(node : Node) : CompileResult<Lang.JavaArgument> {;;}
	createArgumentsRule(value : Rule) : Rule {;;}
}
