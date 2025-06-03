import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { LocatingRule } from "../../../../magmac/app/compile/rule/LocatingRule";
import { NodeListRule } from "../../../../magmac/app/compile/rule/NodeListRule";
import { NodeRule } from "../../../../magmac/app/compile/rule/NodeRule";
import { OrRule } from "../../../../magmac/app/compile/rule/OrRule";
import { PrefixRule } from "../../../../magmac/app/compile/rule/PrefixRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { StringRule } from "../../../../magmac/app/compile/rule/StringRule";
import { StripRule } from "../../../../magmac/app/compile/rule/StripRule";
import { SuffixRule } from "../../../../magmac/app/compile/rule/SuffixRule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
import { DelimitedDivider } from "../../../../magmac/app/compile/rule/divide/DelimitedDivider";
import { DelimitedFolder } from "../../../../magmac/app/compile/rule/fold/DelimitedFolder";
import { CommonLang } from "../../../../magmac/app/lang/CommonLang";
import { CommonRules } from "../../../../magmac/app/lang/CommonRules";
import { JavaRules } from "../../../../magmac/app/lang/JavaRules";
import { LazyRule } from "../../../../magmac/app/lang/LazyRule";
import { MutableLazyRule } from "../../../../magmac/app/lang/MutableLazyRule";
import { OptionNodeListRule } from "../../../../magmac/app/lang/OptionNodeListRule";
import { JavaLang } from "../../../../magmac/app/lang/java/JavaLang";
import { JavaStructureStatement } from "../../../../magmac/app/lang/java/JavaStructureStatement";
import { FunctionSegments } from "../../../../magmac/app/lang/node/FunctionSegments";
import { Modifier } from "../../../../magmac/app/lang/node/Modifier";
import { Parameters } from "../../../../magmac/app/lang/node/Parameters";
export class TypescriptRules {
	createRule() : Rule {return new TypeRule( 0, 0.Statements( 0, new OrRule( 0.of( 0.createWhitespaceRule( ), 0( ), 0( 0), 0( 0)))));;}
	createStructureMemberRule() : Rule {break;break;return new OrRule( 0.of( 0.createWhitespaceRule( ), 0( 0, 0), 0.createStructureStatementRule( 0, 0)));;}
	createMethodRule(definition : Rule, valueLazy : LazyRule) : Rule {break;break;break;break;break;return new TypeRule( 0, new OptionNodeListRule( 0, 0, new SuffixRule( 0, 0)));;}
	createConstructorRule(definition : Rule) : Rule {break;return new TypeRule( 0, new PrefixRule( 0, new SuffixRule( 0, 0)));;}
	createDefinitionRule() : Rule {break;break;break;break;break;break;return 0.set( new OptionNodeListRule( 0, 0.Last( 0, 0, 0), 0));;}
	createTypeRule() : Rule {break;return 0.set( new OrRule( 0.of( 0.JavaTemplateType.createTemplateRule( 0), 0( 0), 0.createSymbolRule( ))));;}
	createStructureRule(type : String) : Rule {break;break;break;return new TypeRule( 0, new PrefixRule( 0, 0));;}
	createImportRule() : Rule {break;break;break;return new TypeRule( 0, new PrefixRule( 0, 0));;}
	createArrayRule(orRule : LazyRule) : TypeRule {return new TypeRule( 0, new SuffixRule( new NodeRule( 0, 0), 0));;}
}
