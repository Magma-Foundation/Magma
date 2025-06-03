import { Consumer } from "../../java/util/function/Consumer";
import { Function } from "../../java/util/function/Function";
import { Predicate } from "../../java/util/function/Predicate";
import { Supplier } from "../../java/util/function/Supplier";
export class None<T> {
	map(mapper : Function<T, R>) : Option<R> {return new None<>( );;}
	isPresent() : boolean {return false;;}
	orElseGet(other : Supplier<T>) : T {return other.get( );;}
	isEmpty() : boolean {return true;;}
	flatMap(mapper : Function<T, Option<R>>) : Option<R> {return new None<>( );;}
	orElse(other : T) : T {return other;;}
	filter(predicate : Predicate<T>) : Option<T> {return this;;}
	or(other : Supplier<Option<T>>) : Option<T> {return other.get( );;}
	ifPresent(consumer : Consumer<T>) : void {;}
}
