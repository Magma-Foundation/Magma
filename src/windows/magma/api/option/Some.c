#include "./Some.h"
export class Some<T> implements Option<T> {
	T value;
	constructor (T value) {
		this.value = value;
	}
}

Option<R> map((arg0 : T) => R mapper) {
	return new Some<R>(mapper/*(arg0 : T) => R*/(this/*auto*/.value));
}
T orElse(T other) {
	return this/*auto*/.value;
}
T orElseGet(() => T supplier) {
	return this/*auto*/.value;
}
Bool isPresent() {
	return true/*auto*/;
}
void ifPresent((arg0 : T) => void consumer) {
	consumer/*(arg0 : T) => void*/(this/*auto*/.value);
}
Option<T> or(() => Option<T> other) {
	return this/*auto*/;
}
Option<R> flatMap((arg0 : T) => Option<R> mapper) {
	return mapper/*(arg0 : T) => Option<R>*/(this/*auto*/.value);
}
Option<T> filter((arg0 : T) => boolean predicate) {
	if (predicate/*(arg0 : T) => boolean*/(this/*auto*/.value)) {
		return this/*auto*/;
	}
	return new None<T>(/*auto*/);
}
Tuple2<Bool, T> toTuple(T other) {
	return new Tuple2Impl<Bool, T>(true/*auto*/, this/*auto*/.value);
}
auto temp(R otherValue) {
	return new Tuple2Impl<T, R>(this/*auto*/.value, otherValue/*auto*/);
}
Option<Tuple2<T, R>> and(() => Option<R> other) {
	return other/*() => Option<R>*/(/*auto*/).map(lambdaDefinition/*auto*/);
}