import { Lists } from "../../../magmac/api/collect/list/Lists";
import { DivideRule } from "../../../magmac/app/compile/rule/DivideRule";
import { LocatingRule } from "../../../magmac/app/compile/rule/LocatingRule";
import { NodeRule } from "../../../magmac/app/compile/rule/NodeRule";
import { OrRule } from "../../../magmac/app/compile/rule/OrRule";
import { PrefixRule } from "../../../magmac/app/compile/rule/PrefixRule";
import { Rule } from "../../../magmac/app/compile/rule/Rule";
import { StringRule } from "../../../magmac/app/compile/rule/StringRule";
import { StripRule } from "../../../magmac/app/compile/rule/StripRule";
import { SuffixRule } from "../../../magmac/app/compile/rule/SuffixRule";
import { TypeRule } from "../../../magmac/app/compile/rule/TypeRule";
import { DelimitedFolder } from "../../../magmac/app/compile/rule/fold/DelimitedFolder";
export class TypescriptLang {
	public static createRule() : Rule {
		return new TypeRule( "root", CommonLang.Statements( "children", new OrRule( Lists.of( CommonLang.createWhitespaceRule( ), TypescriptLang.createImportRule( ), TypescriptLang.createClassRule( "class"), TypescriptLang.createClassRule( "interface")))));
	}
	private static createImportRule() : Rule {
		 segments : Rule=new SuffixRule( new DivideRule( "segments", new DelimitedFolder( '/'), new StringRule( "value")), "\";\n");
		 first : Rule=LocatingRule.First( new StringRule( "child"), " } from \"", segments);
		return new TypeRule( "import", new PrefixRule( "import { ", first));
	}
	private static createClassRule( type : String) : Rule {
		 children : Rule=CommonLang.Statements( "children", TypescriptLang.createStructureMemberRule( ));
		 name : Rule=new StringRule( "name");
		 afterKeyword : Rule=LocatingRule.First( CommonLang.attachTypeParams( name), " {", new SuffixRule( children, "\n}\n"));
		return new TypeRule( type, new PrefixRule( "export " + type + " ", afterKeyword));
	}
	private static createStructureMemberRule() : Rule {
		 definitionRule : Rule=TypescriptLang.createDefinitionRule( );
		 valueLazy : LazyRule=new MutableLazyRule( );
		return new OrRule( Lists.of( CommonLang.createWhitespaceRule( ), TypescriptLang.createMethodRule( definitionRule, valueLazy), CommonLang.createStructureStatementRule( definitionRule, valueLazy)));
	}
	private static createMethodRule( definition : Rule,  valueLazy : LazyRule) : Rule {
		 header : Rule=new PrefixRule( "\n\t", new NodeRule( "header", new OrRule( Lists.of( definition, TypescriptLang.createConstructorRule( definition)))));
		 functionSegmentRule : LazyRule=new MutableLazyRule( );
		 value : LazyRule=CommonLang.initValueRule( functionSegmentRule, valueLazy, " => ", definition);
		 children : Rule=CommonLang.Statements( "children", CommonLang.initFunctionSegmentRule( functionSegmentRule, value, definition));
		 childRule : Rule=new SuffixRule( LocatingRule.First( header, " {", new StripRule( "", children, "after-children")), "}");
		return new TypeRule( "method", new OptionNodeListRule( "children", childRule, new SuffixRule( header, ";")));
	}
	private static createConstructorRule( definition : Rule) : Rule {
		 parametersRule : DivideRule=CommonLang.createParametersRule( definition);
		return new TypeRule( "constructor", new PrefixRule( "constructor(", new SuffixRule( parametersRule, ")")));
	}
	private static createDefinitionRule() : Rule {
		 definition : LazyRule=new MutableLazyRule( );
		 modifiers : Rule=CommonLang.createModifiersRule( );
		 parameters : Rule=CommonLang.createParametersRule( definition);
		 name : Rule=new StringRule( "name");
		 leftRule : Rule=new OptionNodeListRule( "parameters", new SuffixRule( LocatingRule.First( name, "(", parameters), ")"), name);
		 first : Rule=LocatingRule.First( leftRule, " : ", new NodeRule( "type", TypescriptLang.createTypeRule( )));
		return definition.set( new OptionNodeListRule( "modifiers", LocatingRule.Last( modifiers, " ", first), first));
	}
	private static createTypeRule() : Rule {
		 orRule : LazyRule=new MutableLazyRule( );
		orRule.set( new OrRule( Lists.of( CommonLang.createSymbolTypeRule( ), CommonLang.createTemplateRule( ), new TypeRule( "array", new SuffixRule( new NodeRule( "child", orRule), "[]")))));
		return orRule;
	}
}
