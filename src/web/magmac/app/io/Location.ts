export class Location {
	public toString() : String { let joined : var=this.namespace.iter( ).collect( new Joiner( ".")).orElse( "");return joined+"."+this.name;;}
}
