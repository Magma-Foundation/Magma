import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileResultCollector } from "../../../../magmac/app/compile/error/CompileResultCollector";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { NodeList } from "../../../../magmac/app/compile/node/NodeList";
import { NodeListCollector } from "../../../../magmac/app/compile/node/NodeListCollector";
import { Divider } from "../../../../magmac/app/compile/rule/divide/Divider";
import { FoldingDivider } from "../../../../magmac/app/compile/rule/divide/FoldingDivider";
import { Folder } from "../../../../magmac/app/compile/rule/fold/Folder";
import { ValueFolder } from "../../../../magmac/app/lang/ValueFolder";
export class NodeListRule {
	NodeListRule(key : String, childRule : Rule, divider : Divider) : public {break;break;break;;}
	Values(key : String, childRule : Rule) : Rule {return 0;;}
	createNodeListRule(key : String, folder : Folder, childRule : Rule) : Rule {return 0;;}
	lex(input : String) : CompileResult<Node> {return 0;;}
	generate(node : Node) : CompileResult<String> {return 0;;}
}
