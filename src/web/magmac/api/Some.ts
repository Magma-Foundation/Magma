export class Some<T> {
	public map( mapper : Function<T, R>) : Option<R> {return new Some<>( mapper.apply( this.value));;}
	public isPresent() : boolean {return true;;}
	public orElseGet( other : Supplier<T>) : T {return this.value;;}
	public isEmpty() : boolean {return false;;}
	public flatMap( mapper : Function<T, Option<R>>) : Option<R> {return mapper.apply( this.value);;}
	public orElse( other : T) : T {return this.value;;}
	public filter( predicate : Predicate<T>) : Option<T> {if(true){ return this;;}return new None<>( );;}
	public or( other : Supplier<Option<T>>) : Option<T> {return this;;}
	public ifPresent( consumer : Consumer<T>) : void {consumer.accept( this.value);;}
}
