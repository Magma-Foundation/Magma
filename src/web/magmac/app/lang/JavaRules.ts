import { Option } from "../../../magmac/api/Option";
import { List } from "../../../magmac/api/collect/list/List";
import { Lists } from "../../../magmac/api/collect/list/Lists";
import { Iters } from "../../../magmac/api/iter/Iters";
import { ListCollector } from "../../../magmac/api/iter/collect/ListCollector";
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
import { Divider } from "../../../magmac/app/compile/rule/divide/Divider";
import { FoldingDivider } from "../../../magmac/app/compile/rule/divide/FoldingDivider";
import { DelimitedFolder } from "../../../magmac/app/compile/rule/fold/DelimitedFolder";
import { DividingSplitter } from "../../../magmac/app/compile/rule/split/DividingSplitter";
import { JavaDeserializers } from "../../../magmac/app/lang/java/JavaDeserializers";
import { JavaLang } from "../../../magmac/app/lang/java/JavaLang";
import { Arguments } from "../../../magmac/app/lang/node/Arguments";
import { JavaNamespacedNode } from "../../../magmac/app/lang/java/JavaNamespacedNode";
import { Modifier } from "../../../magmac/app/lang/node/Modifier";
import { Operator } from "../../../magmac/app/lang/node/Operator";
import { Parameters } from "../../../magmac/app/lang/node/Parameters";
import { StructureMembers } from "../../../magmac/app/lang/node/StructureMembers";
export class JavaRules {
	createConstructionRule() : Rule {return 0;;}
	createInvokableRule(value : Rule) : Rule {break;break;break;break;return 0;;}
	createRootSegmentRule() : Rule {break;return 0;;}
	createRule() : Rule {return 0;;}
	createStructureRule(keyword : String, structureMember : Rule) : Rule {break;break;break;break;break;break;break;break;break;break;return 0;;}
	createAccessRule(type : String, infix : String, value : LazyRule) : Rule {break;return 0;;}
	deserializeLambdaValueContent(node : Node) : Option<CompileResult<JavaLang.JavaLambdaValueContent>> {return 0;;}
	createYieldRule(value : Rule) : Rule {return 0;;}
	createLambdaRule(value : LazyRule, functionSegment : Rule, infix : String, definition : Rule) : Rule {break;break;break;break;break;return 0;;}
	createStatementRule(rule : Rule) : Rule {break;return 0;;}
	createLambdaParameterRule(definition : Rule) : Rule {return 0;;}
	createBlockHeaderRule(value : Rule, definition : Rule) : Rule {return 0;;}
	createBlockRule(functionSegmentRule : LazyRule, value : Rule, definition : Rule) : Rule {break;return 0;;}
	createBlockRule0(header : Rule, functionSegmentRule : Rule) : TypeRule {break;break;break;return 0;;}
	createConditionalRule(type : String, value : Rule) : Rule {break;break;return 0;;}
	createReturnRule(value : Rule) : Rule {return 0;;}
	createWhitespaceRule() : Rule {return 0;;}
	createStringRule() : Rule {return 0;;}
	createOperationRule(operator : Operator, value : LazyRule) : Rule {return 0;;}
	createCharRule() : Rule {return 0;;}
	createNumberRule() : Rule {return 0;;}
	createNotRule(value : LazyRule) : TypeRule {return 0;;}
	createIndexRule(value : LazyRule) : Rule {break;break;return 0;;}
	initValueRule(segment : Rule, value : LazyRule, lambdaInfix : String, definition : Rule) : LazyRule {return 0;;}
	getValueRules(functionSegment : Rule, value : LazyRule, lambdaInfix : String, definition : Rule) : List<Rule> {break;break;return 0;;}
	createSwitchRule(functionSegmentRule : Rule, value : Rule) : TypeRule {break;break;return 0;;}
	createDefinitionRule() : Rule {break;break;break;break;break;break;break;break;break;return 0;;}
	attachTypeParams(beforeTypeParams : Rule) : Rule {break;return 0;;}
}
