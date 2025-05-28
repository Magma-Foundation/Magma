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
	createRule() : Rule {
		return new TypeRule( "root", CommonLang.Statements( "children", new OrRule( Lists.of( CommonLang.createWhitespaceRule( ), JavaLang.createNamespacedRule( "package"), JavaLang.createNamespacedRule( "import"), JavaLang.createStructureRule( "record"), JavaLang.createStructureRule( "interface"), JavaLang.createStructureRule( "class"), JavaLang.createStructureRule( "enum")))));
	}
	createStructureRule(keyword : String) : Rule {
		 Rule name=new StripRule( FilterRule.Symbol( new StringRule( "name")));
		 Rule beforeContent=new OrRule( Lists.of( new StripRule( new SuffixRule( LocatingRule.First( name, "<", new StringRule( "type-params")), ">")), name));
		 Rule withParameters=new OrRule( Lists.of( new StripRule( new SuffixRule( LocatingRule.First( beforeContent, "(", new StringRule( "parameters")), ")")), beforeContent));
		 Rule withEnds=new OrRule( Lists.of( LocatingRule.First( withParameters, " extends ", new NodeRule( "extended", CommonLang.createTypeRule( ))), withParameters));
		 Rule withImplements=new OrRule( Lists.of( new ContextRule( "With implements", LocatingRule.First( withEnds, " implements ", new NodeRule( "implemented", CommonLang.createTypeRule( )))), new ContextRule( "Without implements", withEnds)));
		 Rule afterKeyword=LocatingRule.First( withImplements, "{", new StripRule( new SuffixRule( CommonLang.Statements( "children", JavaLang.createStructureMemberRule( )), "}")));
		return new TypeRule( keyword, LocatingRule.First( new StringRule( "before-keyword"), keyword+" ", afterKeyword));
	}
	createStructureMemberRule() : OrRule {
		 LazyRule functionSegmentRule=new MutableLazyRule( );
		 LazyRule valueLazy=new MutableLazyRule( );
		 LazyRule value=CommonLang.initValueRule( functionSegmentRule, valueLazy, "->", JavaLang.createDefinitionRule( ));
		 Rule functionSegment=CommonLang.initFunctionSegmentRule( functionSegmentRule, value);
		return new OrRule( Lists.of( CommonLang.createWhitespaceRule( ), JavaLang.createStructureStatementRule( value), JavaLang.createMethodRule( functionSegment)));
	}
	createStructureStatementRule(value1 : LazyRule) : Rule {
		 Rule definition=new OrRule( Lists.of( new NodeRule( "value", new TypeRule( "definition", JavaLang.createDefinitionRule( ))), CommonLang.createAssignmentRule( value1)));
		return new TypeRule( "statement", new StripRule( new SuffixRule( definition, ";")));
	}
	createMethodRule(childRule : Rule) : Rule {
		 NodeRule header=new NodeRule( "header", new OrRule( Lists.of( JavaLang.createDefinitionRule( ), new TypeRule( "constructor", new StripRule( FilterRule.Symbol( new StringRule( "name")))))));
		 Rule parameters=CommonLang.createParametersRule( JavaLang.createDefinitionRule( ));
		 Rule content=CommonLang.Statements( "children", childRule);
		 Rule rightRule=new StripRule( new PrefixRule( "{", new SuffixRule( new StripRule( "", content, "after-children"), "}")));
		 Rule withParams=new OrRule( Lists.of( new SuffixRule( parameters, ");"), LocatingRule.First( parameters, ")", rightRule)));
		return new TypeRule( "method", LocatingRule.First( header, "(", withParams));
	}
	createNamespacedRule(type : String) : Rule {
		 Rule childRule=new DivideRule( "segments", new DelimitedFolder( '.'), new StringRule( "value"));
		return new TypeRule( type, new StripRule( new SuffixRule( new PrefixRule( type+" ", childRule), ";")));
	}
	createDefinitionRule() : Rule {
		 Rule leftRule1=new StringRule( "before-type");
		 Rule rightRule=new NodeRule( "type", CommonLang.createTypeRule( ));
		 Divider divider=new FoldingDivider( new TypeSeparatorFolder( ));
		 Splitter splitter=DividingSplitter.Last( divider, " ");
		 Rule leftRule=new LocatingRule( leftRule1, splitter, rightRule);
		return new StripRule( LocatingRule.Last( leftRule, " ", new StringRule( "name")));
	}
}
