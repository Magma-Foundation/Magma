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
		 Rule segments=new SuffixRule( new DivideRule( "segments", new DelimitedFolder( '/'), new StringRule( "value")), "\";\n");
		 Rule first=LocatingRule.First( new StringRule( "child"), " } from \"", segments);
		return new TypeRule( "import", new PrefixRule( "import { ", first));
	}
	createClassRule(type : String) : Rule {
		 Rule children=CommonLang.Statements( "children", TypescriptLang.createStructureMemberRule( ));
		 Rule name=LocatingRule.First( new StringRule( "name"), " {", new SuffixRule( children, "\n}\n"));
		return new TypeRule( type, new PrefixRule( "export " + type + " ", name));
	}
	createStructureMemberRule() : Rule {
		return new OrRule( Lists.of( CommonLang.createWhitespaceRule( ), TypescriptLang.createMethodRule( ), new TypeRule( "statement", new ExactRule( "\n\ttemp : ?;"))));
	}
	createMethodRule() : TypeRule {
		 Rule header=new OrRule( Lists.of( TypescriptLang.createDefinitionRule( ), TypescriptLang.createConstructorRule( )));
		 PrefixRule header1=new PrefixRule( "\n\t", new NodeRule( "header", header));
		 DivideRule children=CommonLang.Statements( "children", TypescriptLang.createFunctionSegmentRule( ));
		 SuffixRule childRule=new SuffixRule( LocatingRule.First( header1, " {", new StripRule( "", children, "after-children")), "}");
		return new TypeRule( "method", new OptionNodeListRule( "children", childRule, new SuffixRule( header1, ";")));
	}
	createFunctionSegmentRule() : Rule {
		final MutableLazyRule functionSegmentRule=new MutableLazyRule( );
		final MutableLazyRule valueLazy=new MutableLazyRule( );
		return CommonLang.initFunctionSegmentRule( functionSegmentRule, CommonLang.initValueRule( functionSegmentRule, valueLazy));
	}
	createConstructorRule() : TypeRule {
		 DivideRule parametersRule=CommonLang.createParametersRule( TypescriptLang.createDefinitionRule( ));
		return new TypeRule( "constructor", new PrefixRule( "constructor(", new SuffixRule( parametersRule, ")")));
	}
	createDefinitionRule() : Rule {
		 LazyRule definition=new MutableLazyRule( );
		 DivideRule parameters=CommonLang.createParametersRule( definition);
		 Rule name=new StringRule( "name");
		 Rule leftRule=new OrRule( Lists.of( new SuffixRule( LocatingRule.First( name, "(", parameters), ")"), name));
		return definition.set( LocatingRule.First( leftRule, " : ", new NodeRule( "type", TypescriptLang.createTypeRule( ))));
	}
	createTypeRule() : Rule {
		return new OrRule( Lists.of( CommonLang.createSymbolTypeRule( ), CommonLang.createTemplateRule( )));
	}
}
