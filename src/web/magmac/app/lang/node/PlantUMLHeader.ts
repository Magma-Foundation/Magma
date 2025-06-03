import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { ExactRule } from "../../../../magmac/app/compile/rule/ExactRule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
export class PlantUMLHeader {
	static createRule() : TypeRule {return new TypeRule( "header", new ExactRule( "@startuml\nskinparam linetype ortho"));;}
	public serialize() : Node {return new MapNode( "header");;}
}
