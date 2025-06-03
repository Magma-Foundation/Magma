import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { OrRule } from "../../../../magmac/app/compile/rule/OrRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { SuffixRule } from "../../../../magmac/app/compile/rule/SuffixRule";
export class PlantUMLRootSegments {
	public static createRootSegmentRule() : Rule {return new SuffixRule( new OrRule( Lists.of( PlantUMLHeader.createRule( ), PlantUMLFooter.createRule( ), PlantUMLStructure.createStructureRule( "class"), PlantUMLStructure.createStructureRule( "interface"), PlantUMLStructure.createStructureRule( "enum"), PlantUMLInherits.createInheritsRule( ), PlantUMLDependency.createDependencyRule( ))), "\n");;}
}
