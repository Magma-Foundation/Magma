import { Collector } from "../../../../magmac/api/iter/collect/Collector";
import { MapUnitSet } from "../../../../magmac/app/stage/unit/MapUnitSet";
import { Unit } from "../../../../magmac/app/stage/unit/Unit";
import { UnitSet } from "../../../../magmac/app/stage/unit/UnitSet";
export class UnitSetCollector<T> {
	public createInitial() : UnitSet<T> {
		return new MapUnitSet<>( );
	}
	public fold( current : UnitSet<T>,  element : Unit<T>) : UnitSet<T> {
		return current.add( element);
	}
}
