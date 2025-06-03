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
	createRule() : Rule {return new TypeRule( "root", CommonLang.Statements( "children", new OrRule( Lists.of( JavaRules.createWhitespaceRule( ), createImportRule( ), createStructureRule( "class"), createStructureRule( "interface")))));;}
	createStructureMemberRule() : Rule {break;break;return new OrRule( Lists.of( JavaRules.createWhitespaceRule( ), createMethodRule( definitionRule, valueLazy), JavaStructureStatement.createStructureStatementRule( definitionRule, valueLazy)));;}
	createMethodRule(definition : Rule, valueLazy : LazyRule) : Rule {break;break;break;break;break;return new TypeRule( "method", new OptionNodeListRule( "children", childRule, new SuffixRule( header, ";")));;}
	createConstructorRule(definition : Rule) : Rule {break;return new TypeRule( "constructor", new PrefixRule( "constructor(", new SuffixRule( parametersRule, ")")));;}
	createDefinitionRule() : Rule {break;break;break;break;break;break;return definition.set( new OptionNodeListRule( "modifiers", LocatingRule.Last( modifiers, " ", first), first));;}
	createTypeRule() : Rule {break;return type.set( new OrRule( Lists.of( JavaLang.JavaTemplateType.createTemplateRule( type), createArrayRule( type), CommonRules.createSymbolRule( ))));;}
	createStructureRule(type : String) : Rule {break;break;break;return new TypeRule( type, new PrefixRule( "export " + type + " ", afterKeyword));;}
	createImportRule() : Rule {break;break;break;return new TypeRule( "import", new PrefixRule( "import { ", first));;}
	createArrayRule(orRule : LazyRule) : TypeRule {return new TypeRule( "array", new SuffixRule( new NodeRule( "child", orRule), "[]"));;}
}
