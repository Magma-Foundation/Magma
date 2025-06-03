import { Consumer } from "../../java/util/function/Consumer";
import { Function } from "../../java/util/function/Function";
import { Predicate } from "../../java/util/function/Predicate";
import { Supplier } from "../../java/util/function/Supplier";
export class Some<T> {
	map(mapper : Function<T, R>) : Option<R> {;;}
	isPresent() : boolean {;;}
	orElseGet(other : Supplier<T>) : T {;;}
	isEmpty() : boolean {;;}
	flatMap(mapper : Function<T, Option<R>>) : Option<R> {;;}
	orElse(other : T) : T {;;}
	filter(predicate : Predicate<T>) : Option<T> {;;;}
	or(other : Supplier<Option<T>>) : Option<T> {;;}
	ifPresent(consumer : Consumer<T>) : void {;;}
}
