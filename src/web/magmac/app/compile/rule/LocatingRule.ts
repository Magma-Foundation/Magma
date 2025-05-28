import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileErrors } from "../../../../magmac/app/compile/error/error/CompileErrors";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { FirstLocator } from "../../../../magmac/app/compile/rule/locate/FirstLocator";
import { LastLocator } from "../../../../magmac/app/compile/rule/locate/LastLocator";
import { Locator } from "../../../../magmac/app/compile/rule/locate/Locator";
export class LocatingRule {
	temp : ?;
	temp : ?;
	temp : ?;
	LocatingRule(leftRule : Rule, splitter : Splitter, rightRule : Rule) : public;
	First(leftRule : Rule, infix : String, rightRule : Rule) : Rule;
	Last(leftRule : Rule, infix : String, rightRule : Rule) : Rule;
	createLocatingRule(leftRule : Rule, infix : String, rightRule : Rule, locator : Locator) : Rule;
	lex(input : String) : CompileResult<Node>;
	generate(node : Node) : CompileResult<String>;
}
