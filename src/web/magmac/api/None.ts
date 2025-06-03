import { Consumer } from "../../java/util/function/Consumer";
import { Function } from "../../java/util/function/Function";
import { Predicate } from "../../java/util/function/Predicate";
import { Supplier } from "../../java/util/function/Supplier";
export class None<T> {
	map(mapper : Function<T, R>) : Option<R> {return new None<>( );;}
	isPresent() : boolean {return 0;;}
	orElseGet(other : Supplier<T>) : T {return 0.get( );;}
	isEmpty() : boolean {return 0;;}
	flatMap(mapper : Function<T, Option<R>>) : Option<R> {return new None<>( );;}
	orElse(other : T) : T {return 0;;}
	filter(predicate : Predicate<T>) : Option<T> {return 0;;}
	or(other : Supplier<Option<T>>) : Option<T> {return 0.get( );;}
	ifPresent(consumer : Consumer<T>) : void {;}
}
