import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { PrefixRule } from "../../../../magmac/app/compile/rule/PrefixRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { StringRule } from "../../../../magmac/app/compile/rule/StringRule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
export class PlantUMLStructure {
	createStructureRule(type : String) : Rule {return new TypeRule( 0, new PrefixRule( 0, new StringRule( "name")));;}
	serialize() : Node {return new MapNode( 0.type.name( ).toLowerCase( )).withString( "name", 0.name);;}
}
