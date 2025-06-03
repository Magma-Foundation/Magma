import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileErrors } from "../../../../magmac/app/compile/error/error/CompileErrors";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { FirstLocator } from "../../../../magmac/app/compile/rule/locate/FirstLocator";
import { LastLocator } from "../../../../magmac/app/compile/rule/locate/LastLocator";
import { Locator } from "../../../../magmac/app/compile/rule/locate/Locator";
export class LocatingRule {
	LocatingRule(leftRule : Rule, splitter : Splitter, rightRule : Rule) : public {break;break;break;;}
	First(leftRule : Rule, infix : String, rightRule : Rule) : Rule {break;;}
	Last(leftRule : Rule, infix : String, rightRule : Rule) : Rule {break;;}
	createLocatingRule(leftRule : Rule, infix : String, rightRule : Rule, locator : Locator) : Rule {break;;}
	lex(input : String) : CompileResult<Node> {break;;}
	generate(node : Node) : CompileResult<String> {break;;}
}
