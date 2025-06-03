export class PlantUMLFooter {
	static createRule() : TypeRule {return new TypeRule( "footer", new ExactRule( "@enduml"));;}
	public serialize() : Node {return new MapNode( "footer");;}
}
