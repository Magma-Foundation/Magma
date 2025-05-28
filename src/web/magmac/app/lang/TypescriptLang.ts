import { Lists } from "../../../magmac/api/collect/list/Lists";
import { DivideRule } from "../../../magmac/app/compile/rule/DivideRule";
import { ExactRule } from "../../../magmac/app/compile/rule/ExactRule";
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
	createRule() : Rule {
		return new TypeRule( "root", CommonLang.Statements( "children", new OrRule( Lists.of( CommonLang.createWhitespaceRule( ), TypescriptLang.createImportRule( ), TypescriptLang.createClassRule( "class"), TypescriptLang.createClassRule( "interface")))));
	}
	createImportRule() : TypeRule {
		segments : Rule=new SuffixRule( new DivideRule( "segments", new DelimitedFolder( '/'), new StringRule( "value")), "\";\n");
		first : Rule=LocatingRule.First( new StringRule( "child"), " } from \"", segments);
		return new TypeRule( "import", new PrefixRule( "import { ", first));
	}
	createClassRule(type : String) : Rule {
		children : Rule=CommonLang.Statements( "children", TypescriptLang.createStructureMemberRule( ));
		name : Rule=LocatingRule.First( new StringRule( "name"), " {", new SuffixRule( children, "\n}\n"));
		return new TypeRule( type, new PrefixRule( "export " + type + " ", name));
	}
	createStructureMemberRule() : Rule {
		return new OrRule( Lists.of( CommonLang.createWhitespaceRule( ), TypescriptLang.createMethodRule( ), new TypeRule( "statement", new ExactRule( "\n\ttemp : ?;"))));
	}
	createMethodRule() : TypeRule {
		header : Rule=new OrRule( Lists.of( TypescriptLang.createDefinitionRule( ), TypescriptLang.createConstructorRule( )));
		header1 : PrefixRule=new PrefixRule( "\n\t", new NodeRule( "header", header));
		children : DivideRule=CommonLang.Statements( "children", TypescriptLang.createFunctionSegmentRule( ));
		childRule : SuffixRule=new SuffixRule( LocatingRule.First( header1, " {", new StripRule( "", children, "after-children")), "}");
		return new TypeRule( "method", new OptionNodeListRule( "children", childRule, new SuffixRule( header1, ";")));
	}
	createFunctionSegmentRule() : Rule {
		functionSegmentRule : LazyRule=new MutableLazyRule( );
		valueLazy : LazyRule=new MutableLazyRule( );
		value : LazyRule=CommonLang.initValueRule( functionSegmentRule, valueLazy, " => ", TypescriptLang.createDefinitionRule( ));
		return CommonLang.initFunctionSegmentRule( functionSegmentRule, value, TypescriptLang.createDefinitionRule( ));
	}
	createConstructorRule() : TypeRule {
		parametersRule : DivideRule=CommonLang.createParametersRule( TypescriptLang.createDefinitionRule( ));
		return new TypeRule( "constructor", new PrefixRule( "constructor(", new SuffixRule( parametersRule, ")")));
	}
	createDefinitionRule() : Rule {
		definition : LazyRule=new MutableLazyRule( );
		parameters : DivideRule=CommonLang.createParametersRule( definition);
		name : Rule=new StringRule( "name");
		leftRule : Rule=new OrRule( Lists.of( new SuffixRule( LocatingRule.First( name, "(", parameters), ")"), name));
		return definition.set( LocatingRule.First( leftRule, " : ", new NodeRule( "type", TypescriptLang.createTypeRule( ))));
	}
	createTypeRule() : Rule {
		return new OrRule( Lists.of( CommonLang.createSymbolTypeRule( ), CommonLang.createTemplateRule( )));
	}
}
