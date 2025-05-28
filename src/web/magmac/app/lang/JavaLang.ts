import { Lists } from "../../../magmac/api/collect/list/Lists";
import { ContextRule } from "../../../magmac/app/compile/rule/ContextRule";
import { DivideRule } from "../../../magmac/app/compile/rule/DivideRule";
import { ExactRule } from "../../../magmac/app/compile/rule/ExactRule";
import { FilterRule } from "../../../magmac/app/compile/rule/FilterRule";
import { LocatingRule } from "../../../magmac/app/compile/rule/LocatingRule";
import { NodeRule } from "../../../magmac/app/compile/rule/NodeRule";
import { OrRule } from "../../../magmac/app/compile/rule/OrRule";
import { PrefixRule } from "../../../magmac/app/compile/rule/PrefixRule";
import { Rule } from "../../../magmac/app/compile/rule/Rule";
import { Splitter } from "../../../magmac/app/compile/rule/Splitter";
import { StringRule } from "../../../magmac/app/compile/rule/StringRule";
import { StripRule } from "../../../magmac/app/compile/rule/StripRule";
import { SuffixRule } from "../../../magmac/app/compile/rule/SuffixRule";
import { TypeRule } from "../../../magmac/app/compile/rule/TypeRule";
import { Divider } from "../../../magmac/app/compile/rule/divide/Divider";
import { FoldingDivider } from "../../../magmac/app/compile/rule/divide/FoldingDivider";
import { DelimitedFolder } from "../../../magmac/app/compile/rule/fold/DelimitedFolder";
import { DividingSplitter } from "../../../magmac/app/compile/rule/split/DividingSplitter";
export class JavaLang {
	createRule() : Rule {
	}
	createStructureRule(keyword : String) : Rule {
	}
	createStructureMemberRule() : OrRule {
	}
	createStructureStatementRule() : Rule {
	}
	createAssignmentRule(functionSegment : Rule) : Rule {
	}
	createValueRule(functionSegment : Rule) : Rule {
	}
	createIndexRule(value : LazyRule) : TypeRule {
	}
	createLambdaRule(value : LazyRule, functionSegment : Rule) : TypeRule {
	}
	createOperationRule(value : Rule, type : String, infix : String) : TypeRule {
	}
	createSymbolValueRule() : StripRule {
	}
	createAccessRule(value : LazyRule) : Rule {
	}
	createNumberRule() : Rule {
	}
	createStringRule() : TypeRule {
	}
	createCharRule() : TypeRule {
	}
	createInvokableRule(value : Rule) : Rule {
	}
	createMethodRule() : Rule {
	}
	createFunctionSegmentRule() : Rule {
	}
	createBlockRule(functionSegmentRule : LazyRule) : Rule {
	}
	createBlockHeaderRule(functionSegment : Rule) : Rule {
	}
	createConditionalRule(functionSegment : Rule, prefix : String) : StripRule {
	}
	createFunctionSegmentValueRule(functionSegment : Rule) : Rule {
	}
	createPostRule(type : String, suffix : String, functionSegment : Rule) : TypeRule {
	}
	createReturnRule(functionSegment : Rule) : TypeRule {
	}
	createDefinitionRule() : Rule {
	}
	createTypeRule() : Rule {
	}
	createArrayRule(rule : Rule) : TypeRule {
	}
	createVariadicRule(rule : Rule) : TypeRule {
	}
	createNamespacedRule(type : String) : Rule {
	}
}
