import { List } from "../../../magmac/api/collect/list/List";
import { Joiner } from "../../../magmac/api/iter/collect/Joiner";
export class Location {
	public toString() : String { let joined : var=this.namespace.iter( ).collect( new Joiner( ".")).orElse( "");return joined+"."+this.name;;}
}
