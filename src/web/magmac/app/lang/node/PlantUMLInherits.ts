import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { LocatingRule } from "../../../../magmac/app/compile/rule/LocatingRule";
import { StringRule } from "../../../../magmac/app/compile/rule/StringRule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
export class PlantUMLInherits {
	createInheritsRule() : TypeRule {return new TypeRule( 0, 0.First( new StringRule( 0), 0, new StringRule( 0)));;}
	serialize() : Node {return new MapNode( 0).withString( 0, 0.parent).withString( 0, 0.child);;}
}
