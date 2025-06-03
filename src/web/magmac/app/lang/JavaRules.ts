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
import { MultipleCaseValue } from "../../../magmac/app/lang/node/MultipleCaseValue";
import { Operator } from "../../../magmac/app/lang/node/Operator";
import { Parameters } from "../../../magmac/app/lang/node/Parameters";
import { SingleCaseValue } from "../../../magmac/app/lang/node/SingleCaseValue";
import { StructureMembers } from "../../../magmac/app/lang/node/StructureMembers";
export class JavaRules {
	createConstructionRule() : Rule {return new TypeRule( "construction", new StripRule( new PrefixRule( "new ", new NodeRule( "type", JavaLang.JavaTypes.createTypeRule( )))));;}
	createInvokableRule(value : Rule) : Rule {break;break;break;break;return new TypeRule( "invokable", new StripRule( new SuffixRule( new LocatingRule( caller, splitter, arguments), ")")));;}
	createRootSegmentRule() : Rule {break;return new OrRule( Lists.of( createWhitespaceRule( ), JavaNamespacedNode.createNamespacedRule( "package"), JavaNamespacedNode.createNamespacedRule( "import"), JavaRules.createStructureRule( "record", classMemberRule), JavaRules.createStructureRule( "interface", classMemberRule), JavaRules.createStructureRule( "class", classMemberRule), JavaRules.createStructureRule( "enum", classMemberRule)));;}
	createRule() : Rule {return new TypeRule( "root", CommonLang.Statements( "children", JavaRules.createRootSegmentRule( )));;}
	createStructureRule(keyword : String, structureMember : Rule) : Rule {break;break;break;break;break;break;break;break;break;break;return new TypeRule( keyword, LocatingRule.First( Modifier.createModifiersRule( ), keyword+" ", afterKeyword));;}
	createAccessRule(type : String, infix : String, value : LazyRule) : Rule {break;return new TypeRule( type, LocatingRule.Last( new NodeRule( "receiver", value), infix, property));;}
	deserializeLambdaValueContent(node : Node) : Option<CompileResult<JavaLang.JavaLambdaValueContent>> {return Destructors.destructWithType( "value", node).map( 0);;}
	createYieldRule(value : Rule) : Rule {return new TypeRule( "yield", new StripRule( new PrefixRule( "yield ", new NodeRule( "value", value))));;}
	createLambdaRule(value : LazyRule, functionSegment : Rule, infix : String, definition : Rule) : Rule {break;break;break;break;break;return new TypeRule( "lambda", LocatingRule.First( header, infix, new NodeRule( "content", afterInfix)));;}
	createStatementRule(rule : Rule) : Rule {break;return new TypeRule( "statement", new StripRule( new SuffixRule( child, ";")));;}
	createLambdaParameterRule(definition : Rule) : Rule {return NodeListRule.createNodeListRule( "parameters", new ValueFolder( ), new OrRule( Lists.of( definition, CommonRules.createSymbolRule( ))));;}
	createBlockHeaderRule(value : Rule, definition : Rule) : Rule {return new OrRule( Lists.of( new TypeRule( "else", new StripRule( new ExactRule( "else"))), new TypeRule( "try", new StripRule( new ExactRule( "try"))), createConditionalRule( "if", value), createConditionalRule( "while", value), new TypeRule( "catch", new StripRule( new PrefixRule( "catch", new StripRule( new PrefixRule( "(", new SuffixRule( new NodeRule( "definition", definition), ")"))))))));;}
	createBlockRule(functionSegmentRule : LazyRule, value : Rule, definition : Rule) : Rule {break;return createBlockRule0( header, functionSegmentRule);;}
	createBlockRule0(header : Rule, functionSegmentRule : Rule) : TypeRule {break;break;break;return new TypeRule( "block", new StripRule( new SuffixRule( childRule, "}")));;}
	createConditionalRule(type : String, value : Rule) : Rule {break;break;return new TypeRule( type, new StripRule( new PrefixRule( type, childRule)));;}
	createReturnRule(value : Rule) : Rule {return new TypeRule( "return", new StripRule( new PrefixRule( "return ", new NodeRule( "child", value))));;}
	createWhitespaceRule() : Rule {return new TypeRule( "whitespace", new StripRule( new ExactRule( "")));;}
	createStringRule() : Rule {return new TypeRule( "string", new StripRule( new PrefixRule( "\"", new SuffixRule( new StringRule( "value"), "\""))));;}
	createOperationRule(operator : Operator, value : LazyRule) : Rule {return new TypeRule( operator.type( ), LocatingRule.First( new NodeRule( "left", value), operator.text( ), new NodeRule( "right", value)));;}
	createCharRule() : Rule {return new TypeRule( "char", new StripRule( new PrefixRule( "'", new SuffixRule( new StringRule( "value"), "'"))));;}
	createNumberRule() : Rule {return new TypeRule( "number", new StripRule( FilterRule.Number( new StringRule( "value"))));;}
	createNotRule(value : LazyRule) : TypeRule {return new TypeRule( "not", new StripRule( new PrefixRule( "!", new NodeRule( "child", value))));;}
	createIndexRule(value : LazyRule) : Rule {break;break;return new TypeRule( "index", new StripRule( new SuffixRule( LocatingRule.First( parent, "[", argument), "]")));;}
	initValueRule(segment : Rule, value : LazyRule, lambdaInfix : String, definition : Rule) : LazyRule {return value.set( new OrRule( getValueRules( segment, value, lambdaInfix, definition)));;}
	getValueRules(functionSegment : Rule, value : LazyRule, lambdaInfix : String, definition : Rule) : List<Rule> {break;break;return ruleList.addAllLast( operatorLists);;}
	createSwitchRule(functionSegmentRule : Rule, value : Rule) : TypeRule {break;break;return new TypeRule( "switch", createBlockRule0( new StripRule( header), functionSegmentRule));;}
	createDefinitionRule() : Rule {break;break;break;break;break;break;break;break;break;return new TypeRule( "definition", stripRule);;}
	attachTypeParams(beforeTypeParams : Rule) : Rule {break;return new OptionNodeListRule( "type-parameters", new StripRule( new SuffixRule( LocatingRule.First( beforeTypeParams, "<", typeParams), ">")), beforeTypeParams);;}
	createCaseRule(value : Rule, segment : Rule) : Rule {break;break;break;break;break;break;break;return new TypeRule( "case", new StripRule( new PrefixRule( "case", childRule)));;}
}
