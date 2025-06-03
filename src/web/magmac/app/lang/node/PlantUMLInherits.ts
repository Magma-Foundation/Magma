export class PlantUMLInherits {
	static createInheritsRule() : TypeRule {return new TypeRule( "inherits", LocatingRule.First( new StringRule( "parent"), " <|-- ", new StringRule( "child")));;}
	public serialize() : Node {return new MapNode( "inherits").withString( "parent", this.parent).withString( "child", this.child);;}
}
