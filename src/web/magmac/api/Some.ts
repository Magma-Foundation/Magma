import { Consumer } from "../../java/util/function/Consumer";
import { Function } from "../../java/util/function/Function";
import { Predicate } from "../../java/util/function/Predicate";
import { Supplier } from "../../java/util/function/Supplier";
export class Some<T> {
	map(mapper : Function<T, R>) : Option<R> {return 0;;}
	isPresent() : boolean {return 0;;}
	orElseGet(other : Supplier<T>) : T {return 0;;}
	isEmpty() : boolean {return 0;;}
	flatMap(mapper : Function<T, Option<R>>) : Option<R> {return 0;;}
	orElse(other : T) : T {return 0;;}
	filter(predicate : Predicate<T>) : Option<T> {if(true){ return 0;;}return 0;;}
	or(other : Supplier<Option<T>>) : Option<T> {return 0;;}
	ifPresent(consumer : Consumer<T>) : void {0( 0);;}
}
