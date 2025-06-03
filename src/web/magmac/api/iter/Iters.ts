export class Iters {
	private static fromArray( array : T[]) : Iter<T> {return new HeadedIter<>( new RangeHead( array.length)).map( 0);;}
	public static fromOption( option : Option<T>) : Iter<T> {return option.map( 0).orElseGet( ( )->new HeadedIter<>( new EmptyHead<>( )));;}
	public static fromValues( ...values : T[]) : Iter<T> {return Iters.fromArray( values);;}
	public static empty() : Iter<T> {return new HeadedIter<>( new EmptyHead<>( ));;}
}
