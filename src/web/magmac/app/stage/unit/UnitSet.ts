import { Iter } from "../../../../magmac/api/iter/Iter";
export interface UnitSet {
	 iterValues() : Iter<T>;
	 iter() : Iter<Unit<T>>;
	 add( element : Unit<T>) : UnitSet<T>;
}
