import { Lists } from "../../../magmac/api/collect/list/Lists";
import { DivideRule } from "../../../magmac/app/compile/rule/DivideRule";
import { ExactRule } from "../../../magmac/app/compile/rule/ExactRule";
import { LocatingRule } from "../../../magmac/app/compile/rule/LocatingRule";
import { OrRule } from "../../../magmac/app/compile/rule/OrRule";
import { PrefixRule } from "../../../magmac/app/compile/rule/PrefixRule";
import { Rule } from "../../../magmac/app/compile/rule/Rule";
import { StringRule } from "../../../magmac/app/compile/rule/StringRule";
import { SuffixRule } from "../../../magmac/app/compile/rule/SuffixRule";
import { TypeRule } from "../../../magmac/app/compile/rule/TypeRule";
import { StatementFolder } from "../../../magmac/app/compile/rule/fold/StatementFolder";
export class PlantUMLLang {
	public static createRule() : Rule {
		return new DivideRule( "children", new StatementFolder( ), PlantUMLLang.createRootSegmentRule( ));
	}
	private static createRootSegmentRule() : SuffixRule {
		return new SuffixRule( new OrRule( Lists.of( CommonLang.createWhitespaceRule( ), new TypeRule( "start", new ExactRule( "@startuml\nskinparam linetype ortho")), new TypeRule( "end", new ExactRule( "@enduml")), PlantUMLLang.createStructureRule( "class"), PlantUMLLang.createStructureRule( "interface"), PlantUMLLang.createStructureRule( "enum"), new TypeRule( "inherits", LocatingRule.First( new StringRule( "parent"), " <|-- ", new StringRule( "child"))), new TypeRule( "dependency", LocatingRule.First( new StringRule( "child"), " --> ", new StringRule( "parent"))))), "\n");
	}
	private static createStructureRule( type : String) : Rule {
		return new TypeRule( type, new PrefixRule( type+" ", new StringRule( "name")));
	}
}
