#include "./Some.h"
export class Some<T> implements Option<T> {
	value: T;
	constructor (value: T) {
		this.value = value;
	}
}

map<R>(mapper: (arg0 : T) => R): Option<R> {
	return new Some<R>(mapper(this.value));
}
orElse(other: T): T {
	return this.value;
}
orElseGet(supplier: () => T): T {
	return this.value;
}
isPresent(): Bool {
	return true;
}
ifPresent(consumer: (arg0 : T) => void): void {
	consumer(this.value);
}
or(other: () => Option<T>): Option<T> {
	return this;
}
flatMap<R>(mapper: (arg0 : T) => Option<R>): Option<R> {
	return mapper(this.value);
}
filter(predicate: (arg0 : T) => boolean): Option<T> {
	if (predicate(this.value)) {
		return this;
	}
	return new None<T>();
}
toTuple(other: T): Tuple2<Bool, T> {
	return new Tuple2Impl<Bool, T>(true, this.value);
}
and<R>(other: () => Option<R>): Option<Tuple2<T, R>> {
	return other().map((otherValue: R) => new Tuple2Impl<T, R>(this.value, otherValue));
}