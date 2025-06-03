import { Option } from "../../../magmac/api/Option";
import { Lists } from "../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../magmac/app/compile/node/Node";
import { ContextRule } from "../../../magmac/app/compile/rule/ContextRule";
import { ExactRule } from "../../../magmac/app/compile/rule/ExactRule";
import { FilterRule } from "../../../magmac/app/compile/rule/FilterRule";
import { LocatingRule } from "../../../magmac/app/compile/rule/LocatingRule";
import { NodeListRule } from "../../../magmac/app/compile/rule/NodeListRule";
import { NodeRule } from "../../../magmac/app/compile/rule/NodeRule";
import { OrRule } from "../../../magmac/app/compile/rule/OrRule";
import { PrefixRule } from "../../../magmac/app/compile/rule/PrefixRule";
import { Rule } from "../../../magmac/app/compile/rule/Rule";
import { Splitter } from "../../../magmac/app/compile/rule/Splitter";
import { StringRule } from "../../../magmac/app/compile/rule/StringRule";
import { StripRule } from "../../../magmac/app/compile/rule/StripRule";
import { SuffixRule } from "../../../magmac/app/compile/rule/SuffixRule";
import { TypeRule } from "../../../magmac/app/compile/rule/TypeRule";
import { FoldingDivider } from "../../../magmac/app/compile/rule/divide/FoldingDivider";
import { DividingSplitter } from "../../../magmac/app/compile/rule/split/DividingSplitter";
import { Arguments } from "../../../magmac/app/lang/node/Arguments";
import { JavaDefinition } from "../../../magmac/app/lang/java/JavaDefinition";
import { JavaLambdaValueContent } from "../../../magmac/app/lang/node/JavaLambdaValueContent";
import { JavaNamespacedNode } from "../../../magmac/app/lang/java/JavaNamespacedNode";
import { JavaTypes } from "../../../magmac/app/lang/node/JavaTypes";
import { Modifier } from "../../../magmac/app/lang/node/Modifier";
import { Parameters } from "../../../magmac/app/lang/node/Parameters";
import { StructureMembers } from "../../../magmac/app/lang/node/StructureMembers";
import { Symbols } from "../../../magmac/app/lang/node/Symbols";
import { Values } from "../../../magmac/app/lang/node/Values";
export class JavaRules {
	createConstructionRule() : Rule {break;;}
	createInvokableRule(value : Rule) : Rule {break;break;break;break;break;;}
	createRootSegmentRule() : Rule {break;break;;}
	createRule() : Rule {break;;}
	createStructureRule(keyword : String, structureMember : Rule) : Rule {break;break;break;break;break;break;break;break;break;break;break;;}
	createAccessRule(type : String, infix : String, value : LazyRule) : Rule {break;break;;}
	deserializeLambdaValueContent(node : Node) : Option<CompileResult<JavaLambdaValueContent>> {break;;}
	createYieldRule(value : Rule) : Rule {break;;}
	createLambdaRule(value : LazyRule, functionSegment : Rule, infix : String, definition : Rule) : Rule {break;break;break;break;break;break;;}
	createStatementRule(rule : Rule) : Rule {break;break;;}
	createLambdaParameterRule(definition : Rule) : Rule {break;;}
	createBlockHeaderRule(value : Rule, definition : Rule) : Rule {break;;}
	createBlockRule(functionSegmentRule : LazyRule, value : Rule, definition : Rule) : Rule {break;break;;}
	createBlockRule0(header : Rule, functionSegmentRule : Rule) : TypeRule {break;break;break;break;;}
	createConditionalRule(type : String, value : Rule) : Rule {break;break;break;;}
	createReturnRule(value : Rule) : Rule {break;;}
	createWhitespaceRule() : Rule {break;;}
}
