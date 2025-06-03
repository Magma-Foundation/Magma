import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { LocatingRule } from "../../../../magmac/app/compile/rule/LocatingRule";
import { StringRule } from "../../../../magmac/app/compile/rule/StringRule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
export class PlantUMLInherits {
	createInheritsRule() : TypeRule {return new TypeRule( "inherits", 0.First( new StringRule( "parent"), " <|-- ", new StringRule( "child")));;}
	serialize() : Node {return new MapNode( "inherits").withString( "parent", 0.parent).withString( "child", 0.child);;}
}
