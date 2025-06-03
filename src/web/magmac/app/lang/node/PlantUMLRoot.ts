export class PlantUMLRoot {
	public static createRule() : Rule {return NodeListRule.createNodeListRule( "segments", new StatementFolder( ), PlantUMLRootSegments.createRootSegmentRule( ));;}
	public serialize() : Node {return new MapNode( "root").withNodeListAndSerializer( "segments", this.segments, PlantUMLRootSegment.serialize);;}
}
