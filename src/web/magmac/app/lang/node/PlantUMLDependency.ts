export class PlantUMLDependency {
	static createDependencyRule() : TypeRule {return new TypeRule( "dependency", LocatingRule.First( new StringRule( "child"), " --> ", new StringRule( "parent")));;}
	public serialize() : Node {return new MapNode( "dependency").withString( "parent", this.parent).withString( "child", this.child);;}
}
