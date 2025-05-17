#include "./Option.h"
export interface Option<T> {
	Option<R> map((arg0 : T) => R mapper);
	T orElse(T other);
	T orElseGet(() => T supplier);
	Bool isPresent();
	void ifPresent((arg0 : T) => void consumer);
	Option<T> or(() => Option<T> other);
	Option<R> flatMap((arg0 : T) => Option<R> mapper);
	Option<T> filter((arg0 : T) => boolean predicate);
	Tuple2<Bool, T> toTuple(T other);
	Option<Tuple2<T, R>> and(() => Option<R> other);
}
