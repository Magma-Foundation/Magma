import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { PrefixRule } from "../../../../magmac/app/compile/rule/PrefixRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { StringRule } from "../../../../magmac/app/compile/rule/StringRule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
export class PlantUMLStructure {
	public static createStructureRule( type : String) : Rule {return new TypeRule( type, new PrefixRule( type+" ", new StringRule( "name")));;}
	public serialize() : Node {return new MapNode( this.type.name( ).toLowerCase( )).withString( "name", this.name);;}
}
