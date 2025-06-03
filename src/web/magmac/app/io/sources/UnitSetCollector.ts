export class UnitSetCollector<T> {
	public createInitial() : UnitSet<T> {return new MapUnitSet<>( );;}
	public fold( current : UnitSet<T>,  element : Unit<T>) : UnitSet<T> {return current.add( element);;}
}
