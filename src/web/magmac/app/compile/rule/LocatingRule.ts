import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileErrors } from "../../../../magmac/app/compile/error/error/CompileErrors";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { FirstLocator } from "../../../../magmac/app/compile/rule/locate/FirstLocator";
import { LastLocator } from "../../../../magmac/app/compile/rule/locate/LastLocator";
import { Locator } from "../../../../magmac/app/compile/rule/locate/Locator";
export class LocatingRule {
	LocatingRule(leftRule : Rule, splitter : Splitter, rightRule : Rule) : public {break;break;break;;}
	First(leftRule : Rule, infix : String, rightRule : Rule) : Rule {return 0;;}
	Last(leftRule : Rule, infix : String, rightRule : Rule) : Rule {return 0;;}
	createLocatingRule(leftRule : Rule, infix : String, rightRule : Rule, locator : Locator) : Rule {return 0;;}
	lex(input : String) : CompileResult<Node> {return 0;;}
	generate(node : Node) : CompileResult<String> {return 0;;}
}
