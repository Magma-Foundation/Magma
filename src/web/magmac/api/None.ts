export class None<T> {
	public map( mapper : Function<T, R>) : Option<R> {return new None<>( );;}
	public isPresent() : boolean {return false;;}
	public orElseGet( other : Supplier<T>) : T {return other.get( );;}
	public isEmpty() : boolean {return true;;}
	public flatMap( mapper : Function<T, Option<R>>) : Option<R> {return new None<>( );;}
	public orElse( other : T) : T {return other;;}
	public filter( predicate : Predicate<T>) : Option<T> {return this;;}
	public or( other : Supplier<Option<T>>) : Option<T> {return other.get( );;}
	public ifPresent( consumer : Consumer<T>) : void {;}
}
