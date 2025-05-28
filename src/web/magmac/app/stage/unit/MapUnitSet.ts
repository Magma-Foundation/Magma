import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { Map } from "../../../../magmac/api/collect/map/Map";
import { Maps } from "../../../../magmac/api/collect/map/Maps";
import { Iter } from "../../../../magmac/api/iter/Iter";
import { Location } from "../../../../magmac/app/io/Location";
export class MapUnitSet<T> {
	 MapUnitSet() : public {
		this( Maps.empty( ));
	}
	public iter() : Iter<Unit<T>> {
		return this.roots.iter( ).map( ( entry : Tuple2<Location, T>) => new SimpleUnit<>( entry.left( ), entry.right( )));
	}
	public add( element : Unit<T>) : UnitSet<T> {
		return new MapUnitSet<>( element.deconstruct( ( key : Location,  value : T) => this.roots.put( key, value)));
	}
	public iterValues() : Iter<T> {
		return this.roots.iter( ).map( ( entry : Tuple2<Location, T>) => entry.right( ));
	}
}
