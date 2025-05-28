import { Lists } from "../../../magmac/api/collect/list/Lists";
import { DivideRule } from "../../../magmac/app/compile/rule/DivideRule";
import { ExactRule } from "../../../magmac/app/compile/rule/ExactRule";
import { FilterRule } from "../../../magmac/app/compile/rule/FilterRule";
import { LocatingRule } from "../../../magmac/app/compile/rule/LocatingRule";
import { OrRule } from "../../../magmac/app/compile/rule/OrRule";
import { Rule } from "../../../magmac/app/compile/rule/Rule";
import { StringRule } from "../../../magmac/app/compile/rule/StringRule";
import { StripRule } from "../../../magmac/app/compile/rule/StripRule";
import { SuffixRule } from "../../../magmac/app/compile/rule/SuffixRule";
import { TypeRule } from "../../../magmac/app/compile/rule/TypeRule";
import { StatementFolder } from "../../../magmac/app/compile/rule/fold/StatementFolder";
export class CommonLang {
	createWhitespaceRule() : Rule;
	createSymbolTypeRule() : Rule;
	createTemplateRule() : Rule;
	createParametersRule(definition : Rule) : DivideRule;
	Statements(key : String, childRule : Rule) : DivideRule;
}
