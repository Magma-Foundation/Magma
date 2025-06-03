import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileErrors } from "../../../../magmac/app/compile/error/error/CompileErrors";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { FirstLocator } from "../../../../magmac/app/compile/rule/locate/FirstLocator";
import { LastLocator } from "../../../../magmac/app/compile/rule/locate/LastLocator";
import { Locator } from "../../../../magmac/app/compile/rule/locate/Locator";
export class LocatingRule {
	LocatingRule(leftRule : Rule, splitter : Splitter, rightRule : Rule) : public {this.leftRule=leftRule;this.rightRule=rightRule;this.splitter=splitter;;}
	First(leftRule : Rule, infix : String, rightRule : Rule) : Rule {return LocatingRule.createLocatingRule( leftRule, infix, rightRule, new FirstLocator( ));;}
	Last(leftRule : Rule, infix : String, rightRule : Rule) : Rule {return LocatingRule.createLocatingRule( leftRule, infix, rightRule, new LastLocator( ));;}
	createLocatingRule(leftRule : Rule, infix : String, rightRule : Rule, locator : Locator) : Rule {return new LocatingRule( leftRule, new LocatingSplitter( infix, locator), rightRule);;}
	lex(input : String) : CompileResult<Node> {return this.splitter.split( input).map( 0).orElseGet( 0);;}
	generate(node : Node) : CompileResult<String> {return this.leftRule.generate( node).merge( 0, this.splitter.merge);;}
}
