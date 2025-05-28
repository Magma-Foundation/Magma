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
	createWhitespaceRule() : Rule {return new TypeRule( "whitespace", new StripRule( new ExactRule( "")));}
	createSymbolTypeRule() : Rule {return new TypeRule( "symbol-type", new StripRule( FilterRule.Symbol( new StringRule( "value"))));}
	createTemplateRule() : Rule {return new TypeRule( "template", new StripRule( new SuffixRule( LocatingRule.First( new StripRule( new StringRule( "base")), "<", new StringRule( "arguments")), ">")));}
	createParametersRule(definition : Rule) : DivideRule {return new DivideRule( "parameters", new ValueFolder( ), new OrRule( Lists.of( CommonLang.createWhitespaceRule( ), definition)));}
	Statements(key : String, childRule : Rule) : DivideRule {return new DivideRule( key, new StatementFolder( ), childRule);}
}
