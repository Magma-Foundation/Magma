import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { OrRule } from "../../../../magmac/app/compile/rule/OrRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { SuffixRule } from "../../../../magmac/app/compile/rule/SuffixRule";
export class PlantUMLRootSegments {
	createRootSegmentRule() : Rule {return new SuffixRule( new OrRule( 0.of( 0.createRule( ), 0.createRule( ), 0.createStructureRule( 0), 0.createStructureRule( 0), 0.createStructureRule( 0), 0.createInheritsRule( ), 0.createDependencyRule( ))), 0);;}
}
