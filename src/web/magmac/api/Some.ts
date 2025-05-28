import { Consumer } from "../../java/util/function/Consumer";
import { Function } from "../../java/util/function/Function";
import { Predicate } from "../../java/util/function/Predicate";
import { Supplier } from "../../java/util/function/Supplier";
export class Some {
	map(mapper : Function<T, R>) : Option<R> {
		return new Some<>( mapper.apply( this.value));
	}
	isPresent() : boolean {
		return true;
	}
	orElseGet(other : Supplier<T>) : T {
		return this.value;
	}
	isEmpty() : boolean {
		return false;
	}
	flatMap(mapper : Function<T, Option<R>>) : Option<R> {
		return mapper.apply( this.value);
	}
	orElse(other : T) : T {
		return this.value;
	}
	filter(predicate : Predicate<T>) : Option<T> {
		if(predicate.test( this.value)){ 
		return this;}
		return new None<>( );
	}
	or(other : Supplier<Option<T>>) : Option<T> {
		return this;
	}
	ifPresent(consumer : Consumer<T>) : void {
		consumer.accept( this.value);
	}
}
