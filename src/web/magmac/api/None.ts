import { Consumer } from "../../java/util/function/Consumer";
import { Function } from "../../java/util/function/Function";
import { Predicate } from "../../java/util/function/Predicate";
import { Supplier } from "../../java/util/function/Supplier";
export class None<T> {
	map(mapper : Function<T, R>) : Option<R> {break;;}
	isPresent() : boolean {break;;}
	orElseGet(other : Supplier<T>) : T {break;;}
	isEmpty() : boolean {break;;}
	flatMap(mapper : Function<T, Option<R>>) : Option<R> {break;;}
	orElse(other : T) : T {break;;}
	filter(predicate : Predicate<T>) : Option<T> {break;;}
	or(other : Supplier<Option<T>>) : Option<T> {break;;}
	ifPresent(consumer : Consumer<T>) : void {;}
}
