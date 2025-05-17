#include "./Some.h"
export class Some<T> implements Option<T> {
	T value;
	constructor (T value) {
		this.value = value;
	}
}

Option<R> map((arg0 : T) => R mapper) {
	return new Some<R>(mapper(this.value));
}
T orElse(T other) {
	return this.value;
}
T orElseGet(() => T supplier) {
	return this.value;
}
Bool isPresent() {
	return true;
}
void ifPresent((arg0 : T) => void consumer) {
	consumer(this.value);
}
Option<T> or(() => Option<T> other) {
	return this;
}
Option<R> flatMap((arg0 : T) => Option<R> mapper) {
	return mapper(this.value);
}
Option<T> filter((arg0 : T) => boolean predicate) {
	if (predicate(this.value)) {
		return this;
	}
	return new None<T>();
}
Tuple2<Bool, T> toTuple(T other) {
	return new Tuple2Impl<Bool, T>(true, this.value);
}
auto temp(R otherValue) {new Tuple2Impl<T, R>(this.value, otherValue)
}
Option<Tuple2<T, R>> and(() => Option<R> other) {
	return other().map(temp);
}