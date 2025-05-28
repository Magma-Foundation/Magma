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
import { Type } from "../../../java/lang/reflect/Type";
export class JavaLang {
	createRule() : Rule {
		return new TypeRule( "root", CommonLang.Statements( "children", new OrRule( Lists.of( CommonLang.createWhitespaceRule( ), JavaLang.createNamespacedRule( "package"), JavaLang.createNamespacedRule( "import"), JavaLang.createStructureRule( "record"), JavaLang.createStructureRule( "interface"), JavaLang.createStructureRule( "class"), JavaLang.createStructureRule( "enum")))));
	}
	createStructureRule(keyword : String) : Rule {
		 Rule name=new StripRule( FilterRule.Symbol( new StringRule( "name")));
		 Rule beforeContent=new OrRule( Lists.of( new StripRule( new SuffixRule( LocatingRule.First( name, "<", new StringRule( "type-params")), ">")), name));
		 Rule withParameters=new OrRule( Lists.of( new StripRule( new SuffixRule( LocatingRule.First( beforeContent, "(", new StringRule( "parameters")), ")")), beforeContent));
		 Rule withEnds=new OrRule( Lists.of( LocatingRule.First( withParameters, " extends ", new NodeRule( "extended", JavaLang.createTypeRule( ))), withParameters));
		 Rule withImplements=new OrRule( Lists.of( new ContextRule( "With implements", LocatingRule.First( withEnds, " implements ", new NodeRule( "implemented", JavaLang.createTypeRule( )))), new ContextRule( "Without implements", withEnds)));
		 Rule afterKeyword=LocatingRule.First( withImplements, "{", new StripRule( new SuffixRule( CommonLang.Statements( "children", JavaLang.createStructureMemberRule( )), "}")));
		return new TypeRule( keyword, LocatingRule.First( new StringRule( "before-keyword"), keyword+" ", afterKeyword));
	}
	createStructureMemberRule() : OrRule {
		return new OrRule( Lists.of( CommonLang.createWhitespaceRule( ), JavaLang.createStructureStatementRule( ), JavaLang.createMethodRule( )));
	}
	createStructureStatementRule() : Rule {
		 Rule definition=new OrRule( Lists.of( new NodeRule( "value", new TypeRule( "definition", JavaLang.createDefinitionRule( ))), JavaLang.createAssignmentRule( JavaLang.createFunctionSegmentRule( ))));
		return new TypeRule( "statement", new StripRule( new SuffixRule( definition, ";")));
	}
	createAssignmentRule(functionSegment : Rule) : Rule {
		 Rule definition=new OrRule( Lists.of( new NodeRule( "definition", JavaLang.createDefinitionRule( )), new NodeRule( "destination", JavaLang.createValueRule( functionSegment))));
		 Rule value=new NodeRule( "source", JavaLang.createValueRule( functionSegment));
		return new TypeRule( "assignment", LocatingRule.First( definition, "=", value));
	}
	createValueRule(functionSegment : Rule) : Rule {
		 LazyRule value=new MutableLazyRule( );
		return value.set( new OrRule( Lists.of( JavaLang.createLambdaRule( value, functionSegment), new StripRule( new PrefixRule( "!", new NodeRule( "child", value))), JavaLang.createCharRule( ), JavaLang.createStringRule( ), JavaLang.createInvokableRule( value), JavaLang.createNumberRule( ), JavaLang.createAccessRule( value), JavaLang.createSymbolValueRule( ), JavaLang.createOperationRule( value, "add", "+"), JavaLang.createOperationRule( value, "subtract", "-"), JavaLang.createOperationRule( value, "equals", "=="), JavaLang.createOperationRule( value, "less-than", "<"), JavaLang.createOperationRule( value, "and", "&&"), JavaLang.createOperationRule( value, "or", "||"), JavaLang.createOperationRule( value, "not-equals", "!="), JavaLang.createOperationRule( value, "greater-than", ">"), JavaLang.createIndexRule( value))));
	}
	createIndexRule(value : LazyRule) : TypeRule {
		return new TypeRule( "index", new StripRule( new SuffixRule( LocatingRule.First( new NodeRule( "parent", value), "[", new NodeRule( "argument", value)), "]")));
	}
	createLambdaRule(value : LazyRule, functionSegment : Rule) : TypeRule {
		 Rule value1=new OrRule( Lists.of( new StripRule( new PrefixRule( "{", new SuffixRule( CommonLang.Statements( "children", functionSegment), "}"))), new NodeRule( "value", value)));
		return new TypeRule( "lambda", LocatingRule.First( new StringRule( "before-arrow"), "->", value1));
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
		 Rule caller=new ContextRule( "With caller", new SuffixRule( new OrRule( Lists.of( new ContextRule( "As construction", new StripRule( new PrefixRule( "new ", new NodeRule( "type", JavaLang.createTypeRule( ))))), new ContextRule( "As invocation", new NodeRule( "caller", value)))), "("));
		 DivideRule arguments=new DivideRule( "arguments", new ValueFolder( ), new OrRule( Lists.of( CommonLang.createWhitespaceRule( ), value)));
		 Splitter splitter=DividingSplitter.Last( new FoldingDivider( new InvocationFolder( )), "");
		return new TypeRule( "invocation", new StripRule( new SuffixRule( new LocatingRule( caller, splitter, arguments), ")")));
	}
	createMethodRule() : Rule {
		 NodeRule header=new NodeRule( "header", new OrRule( Lists.of( JavaLang.createDefinitionRule( ), new TypeRule( "constructor", new StripRule( FilterRule.Symbol( new StringRule( "name")))))));
		 Rule parameters=CommonLang.createParametersRule( JavaLang.createDefinitionRule( ));
		 Rule content=CommonLang.Statements( "children", JavaLang.createFunctionSegmentRule( ));
		 Rule rightRule=new StripRule( new PrefixRule( "{", new SuffixRule( new StripRule( "", content, "after-children"), "}")));
		 Rule withParams=new OrRule( Lists.of( new SuffixRule( parameters, ");"), LocatingRule.First( parameters, ")", rightRule)));
		return new TypeRule( "method", LocatingRule.First( header, "(", withParams));
	}
	createFunctionSegmentRule() : Rule {
		 LazyRule functionSegmentRule=new MutableLazyRule( );
		 Rule functionSegmentValueRule=JavaLang.createFunctionSegmentValueRule( functionSegmentRule);
		 Rule rule=new OrRule( Lists.of( CommonLang.createWhitespaceRule( ), new TypeRule( "statement", new StripRule( new SuffixRule( new NodeRule( "child", functionSegmentValueRule), ";"))), JavaLang.createBlockRule( functionSegmentRule)));
		return functionSegmentRule.set( new StripRule( "before", rule, ""));
	}
	createBlockRule(functionSegmentRule : LazyRule) : Rule {
		 Rule header=new NodeRule( "header", JavaLang.createBlockHeaderRule( functionSegmentRule));
		 Rule children=CommonLang.Statements( "children", functionSegmentRule);
		 Splitter first=DividingSplitter.First( new FoldingDivider( new BlockFolder( )), "");
		 Rule childRule=new LocatingRule( new SuffixRule( header, "{"), first, children);
		return new TypeRule( "block", new StripRule( new SuffixRule( childRule, "}")));
	}
	createBlockHeaderRule(functionSegment : Rule) : Rule {
		return new OrRule( Lists.of( new TypeRule( "else", new StripRule( new ExactRule( "else"))), new TypeRule( "try", new StripRule( new ExactRule( "try"))), JavaLang.createConditionalRule( functionSegment, "if"), JavaLang.createConditionalRule( functionSegment, "while"), new StripRule( new PrefixRule( "catch", new StripRule( new PrefixRule( "(", new SuffixRule( new NodeRule( "definition", JavaLang.createDefinitionRule( )), ")")))))));
	}
	createConditionalRule(functionSegment : Rule, prefix : String) : StripRule {
		return new StripRule( new PrefixRule( prefix, new StripRule( new PrefixRule( "(", new SuffixRule( new NodeRule( "condition", JavaLang.createValueRule( functionSegment)), ")")))));
	}
	createFunctionSegmentValueRule(functionSegment : Rule) : Rule {
		return new OrRule( Lists.of( JavaLang.createInvokableRule( JavaLang.createValueRule( functionSegment)), JavaLang.createAssignmentRule( functionSegment), JavaLang.createReturnRule( functionSegment), JavaLang.createPostRule( "post-increment", "++", functionSegment), JavaLang.createPostRule( "post-decrement", "--", functionSegment), new TypeRule( "break", new ExactRule( "break")), new TypeRule( "continue", new ExactRule( "continue"))));
	}
	createPostRule(type : String, suffix : String, functionSegment : Rule) : TypeRule {
		return new TypeRule( type, new StripRule( new SuffixRule( new NodeRule( "child", JavaLang.createValueRule( functionSegment)), suffix)));
	}
	createReturnRule(functionSegment : Rule) : TypeRule {
		return new TypeRule( "return", new StripRule( new PrefixRule( "return ", new NodeRule( "value", JavaLang.createValueRule( functionSegment)))));
	}
	createDefinitionRule() : Rule {
		 Rule leftRule1=new StringRule( "before-type");
		 Rule rightRule=new NodeRule( "type", JavaLang.createTypeRule( ));
		 Divider divider=new FoldingDivider( new TypeSeparatorFolder( ));
		 Splitter splitter=DividingSplitter.Last( divider, " ");
		 Rule leftRule=new LocatingRule( leftRule1, splitter, rightRule);
		return new StripRule( LocatingRule.Last( leftRule, " ", new StringRule( "name")));
	}
	createTypeRule() : Rule {
		 LazyRule orRule=new MutableLazyRule( );
		return orRule.set( new OrRule( Lists.of( JavaLang.createVariadicRule( orRule), JavaLang.createArrayRule( orRule), CommonLang.createTemplateRule( ), CommonLang.createSymbolTypeRule( ))));
	}
	createArrayRule(rule : Rule) : TypeRule {
		 NodeRule child=new NodeRule( "child", rule);
		return new TypeRule( "array", new StripRule( new SuffixRule( child, "[]")));
	}
	createVariadicRule(rule : Rule) : TypeRule {
		 NodeRule child=new NodeRule( "child", rule);
		return new TypeRule( "variadic", new StripRule( new SuffixRule( child, "...")));
	}
	createNamespacedRule(type : String) : Rule {
		 Rule childRule=new DivideRule( "segments", new DelimitedFolder( '.'), new StringRule( "value"));
		return new TypeRule( type, new StripRule( new SuffixRule( new PrefixRule( type+" ", childRule), ";")));
	}
}
