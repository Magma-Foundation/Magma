import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { ExactRule } from "../../../../magmac/app/compile/rule/ExactRule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
export class PlantUMLFooter {
	createRule() : TypeRule {return new TypeRule( "footer", new ExactRule( "@enduml"));;}
	serialize() : Node {return new MapNode( "footer");;}
}
