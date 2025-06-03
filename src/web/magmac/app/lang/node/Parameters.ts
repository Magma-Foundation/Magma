import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { NodeListRule } from "../../../../magmac/app/compile/rule/NodeListRule";
import { OrRule } from "../../../../magmac/app/compile/rule/OrRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { JavaDeserializers } from "../../../../magmac/app/lang/JavaDeserializers";
import { JavaRules } from "../../../../magmac/app/lang/JavaRules";
import { ValueFolder } from "../../../../magmac/app/lang/ValueFolder";
import { Deserializers } from "../../../../magmac/app/lang/Deserializers";
import { JavaDefinition } from "../../../../magmac/app/lang/java/JavaDefinition";
import { JavaParameter } from "../../../../magmac/app/lang/java/JavaParameter";
export class Parameters {
	deserialize(node : Node) : CompileResult<JavaParameter> {break;;}
	createParametersRule(definition : Rule) : Rule {break;;}
}
