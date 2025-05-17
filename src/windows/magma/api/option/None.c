#include "./None.h"
export class None<T> implements Option<T> {
}

Option<R> map((arg0 : T) => R mapper) {
	return new None<R>();
}
T orElse(T other) {
	return other;
}
T orElseGet(() => T supplier) {
	return supplier();
}
Bool isPresent() {
	return false;
}
void ifPresent((arg0 : T) => void consumer) {
}
Option<T> or(() => Option<T> other) {
	return other();
}
Option<R> flatMap((arg0 : T) => Option<R> mapper) {
	return new None<R>();
}
Option<T> filter((arg0 : T) => boolean predicate) {
	return new None<T>();
}
Tuple2<Bool, T> toTuple(T other) {
	return new Tuple2Impl<Bool, T>(false, other);
}
Option<Tuple2<T, R>> and(() => Option<R> other) {
	return new None<Tuple2<T, R>>();
}