import { List } from "../../../../magmac/api/collect/list/List";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { NodeListRule } from "../../../../magmac/app/compile/rule/NodeListRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { StatementFolder } from "../../../../magmac/app/compile/rule/fold/StatementFolder";
import { Serializable } from "../../../../magmac/app/lang/Serializable";
export class PlantUMLRoot {
	createRule() : Rule {return NodeListRule.createNodeListRule( "segments", new StatementFolder( ), PlantUMLRootSegments.createRootSegmentRule( ));;}
	serialize() : Node {return new MapNode( "root").withNodeListAndSerializer( "segments", this.segments, PlantUMLRootSegment.serialize);;}
}
