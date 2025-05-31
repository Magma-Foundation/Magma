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
import { FunctionSegment } from "../../../magmac/app/lang/node/FunctionSegment";
import { Modifier } from "../../../magmac/app/lang/node/Modifier";
import { Parameters } from "../../../magmac/app/lang/node/Parameters";
import { StructureStatement } from "../../../magmac/app/lang/node/StructureStatement";
import { Symbols } from "../../../magmac/app/lang/node/Symbols";
import { JavaTemplateType } from "../../../magmac/app/lang/node/JavaTemplateType";
import { TypeScriptImport } from "../../../magmac/app/lang/node/TypeScriptImport";
import { TypescriptStructureNode } from "../../../magmac/app/lang/node/TypescriptStructureNode";
import { Values } from "../../../magmac/app/lang/node/Values";
import { Whitespace } from "../../../magmac/app/lang/node/Whitespace";
export class TypescriptLang {
	createRule() : Rule;
	createStructureMemberRule() : Rule;
	createMethodRule(definition : Rule, valueLazy : LazyRule) : Rule;
	createConstructorRule(definition : Rule) : Rule;
	createDefinitionRule() : Rule;
	createTypeRule() : Rule;
	createArrayRule(orRule : LazyRule) : TypeRule;
}
