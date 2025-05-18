#include "./None.h"
export class None<T> implements Option<T> {
}

Option<R> map((arg0 : T) => R mapper) {
	return new None<R>(/*auto*/);
}
T orElse(T other) {
	return other/*T*/;
}
T orElseGet(() => T supplier) {
	return supplier/*() => T*/(/*auto*/);
}
Bool isPresent() {
	return false/*auto*/;
}
void ifPresent((arg0 : T) => void consumer) {
}
Option<T> or(() => Option<T> other) {
	return other/*() => Option<T>*/(/*auto*/);
}
Option<R> flatMap((arg0 : T) => Option<R> mapper) {
	return new None<R>(/*auto*/);
}
Option<T> filter((arg0 : T) => boolean predicate) {
	return new None<T>(/*auto*/);
}
Tuple2<Bool, T> toTuple(T other) {
	return new Tuple2Impl<Bool, T>(false/*auto*/, other/*T*/);
}
Option<Tuple2<T, R>> and(() => Option<R> other) {
	return new None<Tuple2<T, R>>(/*auto*/);
}