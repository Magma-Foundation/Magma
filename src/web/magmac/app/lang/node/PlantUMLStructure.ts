export class PlantUMLStructure {
	public static createStructureRule( type : String) : Rule {return new TypeRule( type, new PrefixRule( type+" ", new StringRule( "name")));;}
	public serialize() : Node {return new MapNode( this.type.name( ).toLowerCase( )).withString( "name", this.name);;}
}
