export class EmptyHead<T> {
	public next() : Option<T> {return new None<>( );;}
}
