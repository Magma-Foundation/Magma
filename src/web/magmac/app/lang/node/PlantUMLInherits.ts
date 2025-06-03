import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { LocatingRule } from "../../../../magmac/app/compile/rule/LocatingRule";
import { StringRule } from "../../../../magmac/app/compile/rule/StringRule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
export class PlantUMLInherits {
	createInheritsRule() : TypeRule {return new TypeRule( "inherits", LocatingRule.First( new StringRule( "parent"), " <|-- ", new StringRule( "child")));;}
	serialize() : Node {return new MapNode( "inherits").withString( "parent", this.parent).withString( "child", this.child);;}
}
