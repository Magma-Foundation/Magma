import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileErrors } from "../../../../magmac/app/compile/error/error/CompileErrors";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { FirstLocator } from "../../../../magmac/app/compile/rule/locate/FirstLocator";
import { LastLocator } from "../../../../magmac/app/compile/rule/locate/LastLocator";
import { Locator } from "../../../../magmac/app/compile/rule/locate/Locator";
export class LocatingRule {
	LocatingRule(leftRule : Rule, splitter : Splitter, rightRule : Rule) : public {break;break;break;;}
	First(leftRule : Rule, infix : String, rightRule : Rule) : Rule {return 0.createLocatingRule( 0, 0, 0, new FirstLocator( ));;}
	Last(leftRule : Rule, infix : String, rightRule : Rule) : Rule {return 0.createLocatingRule( 0, 0, 0, new LastLocator( ));;}
	createLocatingRule(leftRule : Rule, infix : String, rightRule : Rule, locator : Locator) : Rule {return new LocatingRule( 0, new LocatingSplitter( 0, 0), 0);;}
	lex(input : String) : CompileResult<Node> {return 0.splitter.split( 0).map( 0).orElseGet( 0);;}
	generate(node : Node) : CompileResult<String> {return 0.leftRule.generate( 0).merge( 0, 0.splitter.merge);;}
}
