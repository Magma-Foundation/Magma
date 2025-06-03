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
	createConstructionRule() : Rule {return new TypeRule( 0, new StripRule( new PrefixRule( 0, new NodeRule( 0, 0.JavaTypes.createTypeRule( )))));;}
	createInvokableRule(value : Rule) : Rule {break;break;break;break;return new TypeRule( 0, new StripRule( new SuffixRule( new LocatingRule( 0, 0, 0), 0)));;}
	createRootSegmentRule() : Rule {break;return new OrRule( 0.of( 0( ), 0.createNamespacedRule( 0), 0.createNamespacedRule( 0), 0.createStructureRule( 0, 0), 0.createStructureRule( 0, 0), 0.createStructureRule( 0, 0), 0.createStructureRule( 0, 0)));;}
	createRule() : Rule {return new TypeRule( 0, 0.Statements( 0, 0.createRootSegmentRule( )));;}
	createStructureRule(keyword : String, structureMember : Rule) : Rule {break;break;break;break;break;break;break;break;break;break;return new TypeRule( 0, 0.First( 0.createModifiersRule( ), 0, 0));;}
	createAccessRule(type : String, infix : String, value : LazyRule) : Rule {break;return new TypeRule( 0, 0.Last( new NodeRule( 0, 0), 0, 0));;}
	deserializeLambdaValueContent(node : Node) : Option<CompileResult<JavaLang.JavaLambdaValueContent>> {return 0.destructWithType( 0, 0).map( 0);;}
	createYieldRule(value : Rule) : Rule {return new TypeRule( 0, new StripRule( new PrefixRule( 0, new NodeRule( 0, 0))));;}
	createLambdaRule(value : LazyRule, functionSegment : Rule, infix : String, definition : Rule) : Rule {break;break;break;break;break;return new TypeRule( 0, 0.First( 0, 0, new NodeRule( 0, 0)));;}
	createStatementRule(rule : Rule) : Rule {break;return new TypeRule( 0, new StripRule( new SuffixRule( 0, 0)));;}
	createLambdaParameterRule(definition : Rule) : Rule {return 0.createNodeListRule( 0, new ValueFolder( ), new OrRule( 0.of( 0, 0.createSymbolRule( ))));;}
	createBlockHeaderRule(value : Rule, definition : Rule) : Rule {return new OrRule( 0.of( new TypeRule( 0, new StripRule( new ExactRule( 0))), new TypeRule( 0, new StripRule( new ExactRule( 0))), 0( 0, 0), 0( 0, 0), new TypeRule( 0, new StripRule( new PrefixRule( 0, new StripRule( new PrefixRule( 0, new SuffixRule( new NodeRule( 0, 0), 0))))))));;}
	createBlockRule(functionSegmentRule : LazyRule, value : Rule, definition : Rule) : Rule {break;return 0( 0, 0);;}
	createBlockRule0(header : Rule, functionSegmentRule : Rule) : TypeRule {break;break;break;return new TypeRule( 0, new StripRule( new SuffixRule( 0, 0)));;}
	createConditionalRule(type : String, value : Rule) : Rule {break;break;return new TypeRule( 0, new StripRule( new PrefixRule( 0, 0)));;}
	createReturnRule(value : Rule) : Rule {return new TypeRule( 0, new StripRule( new PrefixRule( 0, new NodeRule( 0, 0))));;}
	createWhitespaceRule() : Rule {return new TypeRule( 0, new StripRule( new ExactRule( 0)));;}
	createStringRule() : Rule {return new TypeRule( 0, new StripRule( new PrefixRule( 0, new SuffixRule( new StringRule( 0), 0))));;}
	createOperationRule(operator : Operator, value : LazyRule) : Rule {return new TypeRule( 0.type( ), 0.First( new NodeRule( 0, 0), 0.text( ), new NodeRule( 0, 0)));;}
	createCharRule() : Rule {return new TypeRule( 0, new StripRule( new PrefixRule( 0, new SuffixRule( new StringRule( 0), 0))));;}
	createNumberRule() : Rule {return new TypeRule( 0, new StripRule( 0.Number( new StringRule( 0))));;}
	createNotRule(value : LazyRule) : TypeRule {return new TypeRule( 0, new StripRule( new PrefixRule( 0, new NodeRule( 0, 0))));;}
	createIndexRule(value : LazyRule) : Rule {break;break;return new TypeRule( 0, new StripRule( new SuffixRule( 0.First( 0, 0, 0), 0)));;}
	initValueRule(segment : Rule, value : LazyRule, lambdaInfix : String, definition : Rule) : LazyRule {return 0.set( new OrRule( 0( 0, 0, 0, 0)));;}
	getValueRules(functionSegment : Rule, value : LazyRule, lambdaInfix : String, definition : Rule) : List<Rule> {break;break;return 0.addAllLast( 0);;}
	createSwitchRule(functionSegmentRule : Rule, value : Rule) : TypeRule {break;break;return new TypeRule( 0, 0( new StripRule( 0), 0));;}
	createDefinitionRule() : Rule {break;break;break;break;break;break;break;break;break;return new TypeRule( 0, 0);;}
	attachTypeParams(beforeTypeParams : Rule) : Rule {break;return new OptionNodeListRule( 0, new StripRule( new SuffixRule( 0.First( 0, 0, 0), 0)), 0);;}
}
