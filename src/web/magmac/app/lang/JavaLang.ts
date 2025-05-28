import { Lists } from "../../../magmac/api/collect/list/Lists";
import { ContextRule } from "../../../magmac/app/compile/rule/ContextRule";
import { DivideRule } from "../../../magmac/app/compile/rule/DivideRule";
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
export class JavaLang {
	public static createRule() : Rule {
		return new TypeRule( "root", CommonLang.Statements( "children", new OrRule( Lists.of( CommonLang.createWhitespaceRule( ), JavaLang.createNamespacedRule( "package"), JavaLang.createNamespacedRule( "import"), JavaLang.createStructureRule( "record"), JavaLang.createStructureRule( "interface"), JavaLang.createStructureRule( "class"), JavaLang.createStructureRule( "enum")))));
	}
	private static createStructureRule( keyword : String) : Rule {
		 name : Rule=new StripRule( FilterRule.Symbol( new StringRule( "name")));
		 beforeContent : Rule=new OrRule( Lists.of( new StripRule( new SuffixRule( LocatingRule.First( name, "<", new StringRule( "type-params")), ">")), name));
		 withParameters : Rule=new OrRule( Lists.of( new StripRule( new SuffixRule( LocatingRule.First( beforeContent, "(", new StringRule( "parameters")), ")")), beforeContent));
		 withEnds : Rule=new OrRule( Lists.of( LocatingRule.First( withParameters, " extends ", new NodeRule( "extended", CommonLang.createTypeRule( ))), withParameters));
		 withImplements : Rule=new OrRule( Lists.of( new ContextRule( "With implements", LocatingRule.First( withEnds, " implements ", new NodeRule( "implemented", CommonLang.createTypeRule( )))), new ContextRule( "Without implements", withEnds)));
		 afterKeyword : Rule=LocatingRule.First( withImplements, "{", new StripRule( new SuffixRule( CommonLang.Statements( "children", JavaLang.createStructureMemberRule( )), "}")));
		return new TypeRule( keyword, LocatingRule.First( new StringRule( "before-keyword"), keyword+" ", afterKeyword));
	}
	private static createStructureMemberRule() : OrRule {
		 functionSegmentRule : LazyRule=new MutableLazyRule( );
		 valueLazy : LazyRule=new MutableLazyRule( );
		 value : LazyRule=CommonLang.initValueRule( functionSegmentRule, valueLazy, "->", JavaLang.createDefinitionRule( ));
		 functionSegment : Rule=CommonLang.initFunctionSegmentRule( functionSegmentRule, value, JavaLang.createDefinitionRule( ));
		return new OrRule( Lists.of( CommonLang.createWhitespaceRule( ), CommonLang.createStructureStatementRule( new TypeRule( "definition", JavaLang.createDefinitionRule( )), value), JavaLang.createMethodRule( functionSegment)));
	}
	private static createMethodRule( childRule : Rule) : Rule {
		 header : NodeRule=new NodeRule( "header", new OrRule( Lists.of( JavaLang.createDefinitionRule( ), new TypeRule( "constructor", new StripRule( FilterRule.Symbol( new StringRule( "name")))))));
		 parameters : Rule=CommonLang.createParametersRule( JavaLang.createDefinitionRule( ));
		 content : Rule=CommonLang.Statements( "children", childRule);
		 rightRule : Rule=new StripRule( new PrefixRule( "{", new SuffixRule( new StripRule( "", content, "after-children"), "}")));
		 withParams : Rule=new OptionNodeListRule( "parameters", new SuffixRule( parameters, ");"), LocatingRule.First( parameters, ")", rightRule));
		return new TypeRule( "method", LocatingRule.First( header, "(", withParams));
	}
	private static createNamespacedRule( type : String) : Rule {
		 childRule : Rule=new DivideRule( "segments", new DelimitedFolder( '.'), new StringRule( "value"));
		return new TypeRule( type, new StripRule( new SuffixRule( new PrefixRule( type+" ", childRule), ";")));
	}
	private static createDefinitionRule() : Rule {
		 modifiers : Rule=CommonLang.createModifiersRule( );
		 annotations : Rule=new DivideRule( "annotations", new DelimitedFolder( '\n'), new StripRule( new PrefixRule( "@", new StringRule( "value"))));
		 beforeTypeParams : Rule=new OrRule( Lists.of( LocatingRule.Last( annotations, "\n", modifiers), modifiers));
		 typeParams : Rule=new DivideRule( "type-parameters", new ValueFolder( ), new StringRule( "value"));
		 leftRule1 : Rule=new OrRule( Lists.of( new StripRule( new SuffixRule( LocatingRule.First( beforeTypeParams, "<", typeParams), ">")), beforeTypeParams));
		 rightRule : Rule=new NodeRule( "type", CommonLang.createTypeRule( ));
		 divider : Divider=new FoldingDivider( new TypeSeparatorFolder( ));
		 splitter : Splitter=DividingSplitter.Last( divider, " ");
		 leftRule : Rule=new LocatingRule( leftRule1, splitter, rightRule);
		return new StripRule( LocatingRule.Last( leftRule, " ", new StringRule( "name")));
	}
}
