export class ListCollector<T> {
	public createInitial() : List<T> {return Lists.empty( );;}
	public fold( current : List<T>,  element : T) : List<T> {return current.addLast( element);;}
}
