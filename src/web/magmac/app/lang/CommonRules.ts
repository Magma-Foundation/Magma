import { FilterRule } from "../../../magmac/app/compile/rule/FilterRule";
import { Rule } from "../../../magmac/app/compile/rule/Rule";
import { StringRule } from "../../../magmac/app/compile/rule/StringRule";
import { StripRule } from "../../../magmac/app/compile/rule/StripRule";
import { TypeRule } from "../../../magmac/app/compile/rule/TypeRule";
export class CommonRules {
	createSymbolRule(key : String) : Rule {return new StripRule( 0.Symbol( new StringRule( 0)));;}
	createSymbolRule() : Rule {return new TypeRule( 0, 0( 0));;}
}
