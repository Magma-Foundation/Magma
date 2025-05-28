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
import { DelimitedFolder } from "../../../magmac/app/compile/rule/fold/DelimitedFolder";
import { StatementFolder } from "../../../magmac/app/compile/rule/fold/StatementFolder";
import { DividingSplitter } from "../../../magmac/app/compile/rule/split/DividingSplitter";
export class CommonLang {
	static createWhitespaceRule() : Rule {
		return new TypeRule( "whitespace", new StripRule( new ExactRule( "")));
	}
	static createSymbolTypeRule() : Rule {
		return new TypeRule( "symbol-type", new StripRule( FilterRule.Symbol( new StringRule( "value"))));
	}
	static createTemplateRule() : Rule {
		return new TypeRule( "template", new StripRule( new SuffixRule( LocatingRule.First( new StripRule( new StringRule( "base")), "<", new StringRule( "arguments")), ">")));
	}
	static createParametersRule( definition : Rule) : DivideRule {
		return new DivideRule( "parameters", new ValueFolder( ), new OrRule( Lists.of( CommonLang.createWhitespaceRule( ), definition)));
	}
	public static Statements( key : String,  childRule : Rule) : DivideRule {
		return new DivideRule( key, new StatementFolder( ), childRule);
	}
	private static createAssignmentRule( definition : Rule,  value : Rule) : Rule {
		 before : Rule=new OrRule( Lists.of( new NodeRule( "definition", definition), new NodeRule( "destination", value)));
		 source : Rule=new NodeRule( "source", value);
		return new TypeRule( "assignment", LocatingRule.First( before, "=", source));
	}
	static initValueRule( segment : Rule,  value : LazyRule,  lambdaInfix : String,  definition : Rule) : LazyRule {
		return value.set( new OrRule( Lists.of( CommonLang.createLambdaRule( value, segment, lambdaInfix, definition), new StripRule( new PrefixRule( "!", new NodeRule( "child", value))), CommonLang.createCharRule( ), CommonLang.createStringRule( ), CommonLang.createInvokableRule( value), CommonLang.createNumberRule( ), CommonLang.createAccessRule( value), CommonLang.createSymbolValueRule( ), CommonLang.createOperationRule( value, "add", "+"), CommonLang.createOperationRule( value, "subtract", "-"), CommonLang.createOperationRule( value, "equals", "=="), CommonLang.createOperationRule( value, "less-than", "<"), CommonLang.createOperationRule( value, "and", "&&"), CommonLang.createOperationRule( value, "or", "||"), CommonLang.createOperationRule( value, "not-equals", "!="), CommonLang.createOperationRule( value, "greater-than", ">"), CommonLang.createIndexRule( value))));
	}
	private static createIndexRule( value : LazyRule) : Rule {
		return new TypeRule( "index", new StripRule( new SuffixRule( LocatingRule.First( new NodeRule( "parent", value), "[", new NodeRule( "argument", value)), "]")));
	}
	private static createLambdaRule( value : LazyRule,  functionSegment : Rule,  infix : String,  definition : Rule) : Rule {
		 value1 : Rule=new OrRule( Lists.of( new StripRule( new PrefixRule( "{", new SuffixRule( CommonLang.Statements( "children", functionSegment), "}"))), new NodeRule( "value", value)));
		return new TypeRule( "lambda", LocatingRule.First( new StripRule( new PrefixRule( "(", new SuffixRule( new DivideRule( "parameters", new ValueFolder( ), definition), ")"))), infix, value1));
	}
	private static createOperationRule( value : Rule,  type : String,  infix : String) : Rule {
		return new TypeRule( type, LocatingRule.First( new NodeRule( "left", value), infix, new NodeRule( "right", value)));
	}
	private static createSymbolValueRule() : StripRule {
		return new StripRule( FilterRule.Symbol( new StringRule( "value")));
	}
	private static createAccessRule( value : LazyRule) : Rule {
		 property : StripRule=new StripRule( FilterRule.Symbol( new StringRule( "property")));
		return new TypeRule( "access", LocatingRule.Last( new NodeRule( "instance", value), ".", property));
	}
	private static createNumberRule() : Rule {
		return new TypeRule( "number", new StripRule( FilterRule.Number( new StringRule( "value"))));
	}
	private static createStringRule() : Rule {
		return new TypeRule( "string", new StripRule( new PrefixRule( "\"", new SuffixRule( new StringRule( "value"), "\""))));
	}
	private static createCharRule() : Rule {
		return new TypeRule( "char", new StripRule( new PrefixRule( "'", new SuffixRule( new StringRule( "value"), "'"))));
	}
	private static createInvokableRule( value : Rule) : Rule {
		 caller : Rule=new ContextRule( "With caller", new SuffixRule( new OrRule( Lists.of( new ContextRule( "As construction", new StripRule( new PrefixRule( "new ", new NodeRule( "type", CommonLang.createTypeRule( ))))), new ContextRule( "As invocation", new NodeRule( "caller", value)))), "("));
		 arguments : DivideRule=new DivideRule( "arguments", new ValueFolder( ), new OrRule( Lists.of( CommonLang.createWhitespaceRule( ), value)));
		 splitter : Splitter=DividingSplitter.Last( new FoldingDivider( new InvocationFolder( )), "");
		return new TypeRule( "invocation", new StripRule( new SuffixRule( new LocatingRule( caller, splitter, arguments), ")")));
	}
	static initFunctionSegmentRule( functionSegmentRule : LazyRule,  value : Rule,  definition : Rule) : Rule {
		 functionSegmentValueRule : Rule=CommonLang.createFunctionSegmentValueRule( value, definition);
		 rule : Rule=new OrRule( Lists.of( CommonLang.createWhitespaceRule( ), CommonLang.createStatementRule( functionSegmentValueRule), CommonLang.createBlockRule( functionSegmentRule, value, definition)));
		return functionSegmentRule.set( new StripRule( "before", rule, ""));
	}
	private static createStatementRule( rule : Rule) : Rule {
		 child : NodeRule=new NodeRule( "child", rule);
		return new TypeRule( "statement", new StripRule( new SuffixRule( child, ";")));
	}
	private static createBlockRule( functionSegmentRule : LazyRule,  value : Rule,  definition : Rule) : Rule {
		 header : Rule=new NodeRule( "header", CommonLang.createBlockHeaderRule( value, definition));
		 children : Rule=CommonLang.Statements( "children", functionSegmentRule);
		 first : Splitter=DividingSplitter.First( new FoldingDivider( new BlockFolder( )), "");
		 childRule : Rule=new LocatingRule( new SuffixRule( header, "{"), first, children);
		return new TypeRule( "block", new StripRule( new SuffixRule( childRule, "}")));
	}
	private static createBlockHeaderRule( value : Rule,  definition : Rule) : Rule {
		return new OrRule( Lists.of( new TypeRule( "else", new StripRule( new ExactRule( "else"))), new TypeRule( "try", new StripRule( new ExactRule( "try"))), CommonLang.createConditionalRule( "if", value), CommonLang.createConditionalRule( "while", value), new StripRule( new PrefixRule( "catch", new StripRule( new PrefixRule( "(", new SuffixRule( new NodeRule( "definition", definition), ")")))))));
	}
	private static createConditionalRule( prefix : String,  value : Rule) : StripRule {
		return new StripRule( new PrefixRule( prefix, new StripRule( new PrefixRule( "(", new SuffixRule( new NodeRule( "condition", value), ")")))));
	}
	private static createFunctionSegmentValueRule( value : Rule,  definition : Rule) : Rule {
		return new OrRule( Lists.of( CommonLang.createInvokableRule( value), CommonLang.createAssignmentRule( definition, value), CommonLang.createReturnRule( value), CommonLang.createPostRule( "post-increment", "++", value), CommonLang.createPostRule( "post-decrement", "--", value), new TypeRule( "break", new ExactRule( "break")), new TypeRule( "continue", new ExactRule( "continue"))));
	}
	private static createPostRule( type : String,  suffix : String,  value : Rule) : Rule {
		return new TypeRule( type, new StripRule( new SuffixRule( new NodeRule( "child", value), suffix)));
	}
	private static createReturnRule( value : Rule) : Rule {
		return new TypeRule( "return", new StripRule( new PrefixRule( "return ", new NodeRule( "value", value))));
	}
	static createTypeRule() : Rule {
		 orRule : LazyRule=new MutableLazyRule( );
		return orRule.set( new OrRule( Lists.of( CommonLang.createVariadicRule( orRule), CommonLang.createArrayRule( orRule), CommonLang.createTemplateRule( ), CommonLang.createSymbolTypeRule( ))));
	}
	private static createArrayRule( rule : Rule) : Rule {
		 child : NodeRule=new NodeRule( "child", rule);
		return new TypeRule( "array", new StripRule( new SuffixRule( child, "[]")));
	}
	private static createVariadicRule( rule : Rule) : Rule {
		 child : NodeRule=new NodeRule( "child", rule);
		return new TypeRule( "variadic", new StripRule( new SuffixRule( child, "...")));
	}
	static createStructureStatementRule( definition1 : Rule,  value : LazyRule) : Rule {
		 definition : Rule=new NodeRule( "value", new OrRule( Lists.of( definition1, CommonLang.createAssignmentRule( definition1, value))));
		return new TypeRule( "statement", new StripRule( new SuffixRule( definition, ";")));
	}
	static createModifiersRule() : Rule {
		return new StripRule( new DivideRule( "modifiers", new DelimitedFolder( ' '), new StringRule( "value")));
	}
	static attachTypeParams( beforeTypeParams : Rule) : Rule {
		 typeParams : Rule=new DivideRule( "type-parameters", new ValueFolder( ), new StringRule( "value"));
		return new OptionNodeListRule( "type-parameters", new StripRule( new SuffixRule( LocatingRule.First( beforeTypeParams, "<", typeParams), ">")), beforeTypeParams);
	}
}
