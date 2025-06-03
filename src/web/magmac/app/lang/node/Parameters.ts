import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { NodeListRule } from "../../../../magmac/app/compile/rule/NodeListRule";
import { OrRule } from "../../../../magmac/app/compile/rule/OrRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { ValueFolder } from "../../../../magmac/app/lang/ValueFolder";
import { Deserializers } from "../../../../magmac/app/lang/Deserializers";
export class Parameters {
	deserialize(node : Node) : CompileResult<JavaParameter> {;;}
	createParametersRule(definition : Rule) : Rule {;;}
}
