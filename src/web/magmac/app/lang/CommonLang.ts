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
import { FoldingDivider } from "../../../magmac/app/compile/rule/divide/FoldingDivider";
import { StatementFolder } from "../../../magmac/app/compile/rule/fold/StatementFolder";
import { DividingSplitter } from "../../../magmac/app/compile/rule/split/DividingSplitter";
export class CommonLang {
	createWhitespaceRule() : Rule {
		return new TypeRule( "whitespace", new StripRule( new ExactRule( "")));
	}
	createSymbolTypeRule() : Rule {
		return new TypeRule( "symbol-type", new StripRule( FilterRule.Symbol( new StringRule( "value"))));
	}
	createTemplateRule() : Rule {
		return new TypeRule( "template", new StripRule( new SuffixRule( LocatingRule.First( new StripRule( new StringRule( "base")), "<", new StringRule( "arguments")), ">")));
	}
	createParametersRule(definition : Rule) : DivideRule {
		return new DivideRule( "parameters", new ValueFolder( ), new OrRule( Lists.of( CommonLang.createWhitespaceRule( ), definition)));
	}
	Statements(key : String, childRule : Rule) : DivideRule {
		return new DivideRule( key, new StatementFolder( ), childRule);
	}
	createAssignmentRule(value : Rule) : Rule {
		 Rule definition=new OrRule( Lists.of( new NodeRule( "definition", JavaLang.createDefinitionRule( )), new NodeRule( "destination", value)));
		 Rule source=new NodeRule( "source", value);
		return new TypeRule( "assignment", LocatingRule.First( definition, "=", source));
	}
	initValueRule(segment : Rule, value : LazyRule, lambdaInfix : String, definition : Rule) : LazyRule {
		return value.set( new OrRule( Lists.of( CommonLang.createLambdaRule( value, segment, lambdaInfix, definition), new StripRule( new PrefixRule( "!", new NodeRule( "child", value))), CommonLang.createCharRule( ), CommonLang.createStringRule( ), CommonLang.createInvokableRule( value), CommonLang.createNumberRule( ), CommonLang.createAccessRule( value), CommonLang.createSymbolValueRule( ), CommonLang.createOperationRule( value, "add", "+"), CommonLang.createOperationRule( value, "subtract", "-"), CommonLang.createOperationRule( value, "equals", "=="), CommonLang.createOperationRule( value, "less-than", "<"), CommonLang.createOperationRule( value, "and", "&&"), CommonLang.createOperationRule( value, "or", "||"), CommonLang.createOperationRule( value, "not-equals", "!="), CommonLang.createOperationRule( value, "greater-than", ">"), CommonLang.createIndexRule( value))));
	}
	createIndexRule(value : LazyRule) : TypeRule {
		return new TypeRule( "index", new StripRule( new SuffixRule( LocatingRule.First( new NodeRule( "parent", value), "[", new NodeRule( "argument", value)), "]")));
	}
	createLambdaRule(value : LazyRule, functionSegment : Rule, infix : String, definition : Rule) : TypeRule {
		 Rule value1=new OrRule( Lists.of( new StripRule( new PrefixRule( "{", new SuffixRule( CommonLang.Statements( "children", functionSegment), "}"))), new NodeRule( "value", value)));
		return new TypeRule( "lambda", LocatingRule.First( new StripRule( new PrefixRule( "(", new SuffixRule( new DivideRule( "parameters", new ValueFolder( ), definition), ")"))), infix, value1));
	}
	createOperationRule(value : Rule, type : String, infix : String) : TypeRule {
		return new TypeRule( type, LocatingRule.First( new NodeRule( "left", value), infix, new NodeRule( "right", value)));
	}
	createSymbolValueRule() : StripRule {
		return new StripRule( FilterRule.Symbol( new StringRule( "value")));
	}
	createAccessRule(value : LazyRule) : Rule {
		 StripRule property=new StripRule( FilterRule.Symbol( new StringRule( "property")));
		return new TypeRule( "access", LocatingRule.Last( new NodeRule( "instance", value), ".", property));
	}
	createNumberRule() : Rule {
		return new TypeRule( "number", new StripRule( FilterRule.Number( new StringRule( "value"))));
	}
	createStringRule() : TypeRule {
		return new TypeRule( "string", new StripRule( new PrefixRule( "\"", new SuffixRule( new StringRule( "value"), "\""))));
	}
	createCharRule() : TypeRule {
		return new TypeRule( "char", new StripRule( new PrefixRule( "'", new SuffixRule( new StringRule( "value"), "'"))));
	}
	createInvokableRule(value : Rule) : Rule {
		 Rule caller=new ContextRule( "With caller", new SuffixRule( new OrRule( Lists.of( new ContextRule( "As construction", new StripRule( new PrefixRule( "new ", new NodeRule( "type", CommonLang.createTypeRule( ))))), new ContextRule( "As invocation", new NodeRule( "caller", value)))), "("));
		 DivideRule arguments=new DivideRule( "arguments", new ValueFolder( ), new OrRule( Lists.of( CommonLang.createWhitespaceRule( ), value)));
		 Splitter splitter=DividingSplitter.Last( new FoldingDivider( new InvocationFolder( )), "");
		return new TypeRule( "invocation", new StripRule( new SuffixRule( new LocatingRule( caller, splitter, arguments), ")")));
	}
	initFunctionSegmentRule(functionSegmentRule : LazyRule, value : Rule) : Rule {
		 Rule functionSegmentValueRule=CommonLang.createFunctionSegmentValueRule( value);
		 Rule rule=new OrRule( Lists.of( CommonLang.createWhitespaceRule( ), CommonLang.createStatementRule( functionSegmentValueRule), CommonLang.createBlockRule( functionSegmentRule, value)));
		return functionSegmentRule.set( new StripRule( "before", rule, ""));
	}
	createStatementRule(rule : Rule) : TypeRule {
		 NodeRule child=new NodeRule( "child", rule);
		return new TypeRule( "statement", new StripRule( new SuffixRule( child, ";")));
	}
	createBlockRule(functionSegmentRule : LazyRule, value : Rule) : Rule {
		 Rule header=new NodeRule( "header", CommonLang.createBlockHeaderRule( value));
		 Rule children=CommonLang.Statements( "children", functionSegmentRule);
		 Splitter first=DividingSplitter.First( new FoldingDivider( new BlockFolder( )), "");
		 Rule childRule=new LocatingRule( new SuffixRule( header, "{"), first, children);
		return new TypeRule( "block", new StripRule( new SuffixRule( childRule, "}")));
	}
	createBlockHeaderRule(value : Rule) : Rule {
		return new OrRule( Lists.of( new TypeRule( "else", new StripRule( new ExactRule( "else"))), new TypeRule( "try", new StripRule( new ExactRule( "try"))), CommonLang.createConditionalRule( "if", value), CommonLang.createConditionalRule( "while", value), new StripRule( new PrefixRule( "catch", new StripRule( new PrefixRule( "(", new SuffixRule( new NodeRule( "definition", JavaLang.createDefinitionRule( )), ")")))))));
	}
	createConditionalRule(prefix : String, value : Rule) : StripRule {
		return new StripRule( new PrefixRule( prefix, new StripRule( new PrefixRule( "(", new SuffixRule( new NodeRule( "condition", value), ")")))));
	}
	createFunctionSegmentValueRule(value : Rule) : Rule {
		return new OrRule( Lists.of( CommonLang.createInvokableRule( value), CommonLang.createAssignmentRule( value), CommonLang.createReturnRule( value), CommonLang.createPostRule( "post-increment", "++", value), CommonLang.createPostRule( "post-decrement", "--", value), new TypeRule( "break", new ExactRule( "break")), new TypeRule( "continue", new ExactRule( "continue"))));
	}
	createPostRule(type : String, suffix : String, value : Rule) : TypeRule {
		return new TypeRule( type, new StripRule( new SuffixRule( new NodeRule( "child", value), suffix)));
	}
	createReturnRule(value : Rule) : TypeRule {
		return new TypeRule( "return", new StripRule( new PrefixRule( "return ", new NodeRule( "value", value))));
	}
	createTypeRule() : Rule {
		 LazyRule orRule=new MutableLazyRule( );
		return orRule.set( new OrRule( Lists.of( CommonLang.createVariadicRule( orRule), CommonLang.createArrayRule( orRule), CommonLang.createTemplateRule( ), CommonLang.createSymbolTypeRule( ))));
	}
	createArrayRule(rule : Rule) : TypeRule {
		 NodeRule child=new NodeRule( "child", rule);
		return new TypeRule( "array", new StripRule( new SuffixRule( child, "[]")));
	}
	createVariadicRule(rule : Rule) : TypeRule {
		 NodeRule child=new NodeRule( "child", rule);
		return new TypeRule( "variadic", new StripRule( new SuffixRule( child, "...")));
	}
}
