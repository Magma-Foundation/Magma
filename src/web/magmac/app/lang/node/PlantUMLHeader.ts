import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { ExactRule } from "../../../../magmac/app/compile/rule/ExactRule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
export class PlantUMLHeader {
	createRule() : TypeRule {return new TypeRule( 0, new ExactRule( 0));;}
	serialize() : Node {return new MapNode( 0);;}
}
