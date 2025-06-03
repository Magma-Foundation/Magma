export class PlantUMLHeader {
	static createRule() : TypeRule {return new TypeRule( "header", new ExactRule( "@startuml\nskinparam linetype ortho"));;}
	public serialize() : Node {return new MapNode( "header");;}
}
