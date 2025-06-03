import { FilterRule } from "../../../magmac/app/compile/rule/FilterRule";
import { Rule } from "../../../magmac/app/compile/rule/Rule";
import { StringRule } from "../../../magmac/app/compile/rule/StringRule";
import { StripRule } from "../../../magmac/app/compile/rule/StripRule";
import { TypeRule } from "../../../magmac/app/compile/rule/TypeRule";
export class CommonRules {
	createSymbolRule(key : String) : Rule {return new StripRule( FilterRule.Symbol( new StringRule( key)));;}
	createSymbolRule() : Rule {return new TypeRule( "symbol", createSymbolRule( "value"));;}
}
