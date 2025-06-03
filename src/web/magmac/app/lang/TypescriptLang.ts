import { Lists } from "../../../magmac/api/collect/list/Lists";
import { LocatingRule } from "../../../magmac/app/compile/rule/LocatingRule";
import { NodeRule } from "../../../magmac/app/compile/rule/NodeRule";
import { OrRule } from "../../../magmac/app/compile/rule/OrRule";
import { PrefixRule } from "../../../magmac/app/compile/rule/PrefixRule";
import { Rule } from "../../../magmac/app/compile/rule/Rule";
import { StringRule } from "../../../magmac/app/compile/rule/StringRule";
import { StripRule } from "../../../magmac/app/compile/rule/StripRule";
import { SuffixRule } from "../../../magmac/app/compile/rule/SuffixRule";
import { TypeRule } from "../../../magmac/app/compile/rule/TypeRule";
import { FunctionSegments } from "../../../magmac/app/lang/node/FunctionSegments";
import { Modifier } from "../../../magmac/app/lang/node/Modifier";
import { Parameters } from "../../../magmac/app/lang/node/Parameters";
import { JavaStructureStatement } from "../../../magmac/app/lang/java/JavaStructureStatement";
import { Symbols } from "../../../magmac/app/lang/node/Symbols";
import { JavaTemplateType } from "../../../magmac/app/lang/node/JavaTemplateType";
import { TypeScriptImport } from "../../../magmac/app/lang/node/TypeScriptImport";
import { TypescriptStructureNode } from "../../../magmac/app/lang/node/TypescriptStructureNode";
import { Values } from "../../../magmac/app/lang/node/Values";
export class TypescriptLang {
	createRule() : Rule {return 0;;}
	createStructureMemberRule() : Rule {break;break;return 0;;}
	createMethodRule(definition : Rule, valueLazy : LazyRule) : Rule {break;break;break;break;break;return 0;;}
	createConstructorRule(definition : Rule) : Rule {break;return 0;;}
	createDefinitionRule() : Rule {break;break;break;break;break;break;return 0;;}
	createTypeRule() : Rule {break;return 0;;}
	createArrayRule(orRule : LazyRule) : TypeRule {return 0;;}
}
