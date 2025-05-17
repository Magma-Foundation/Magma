#include "./None.h"
export class None<T> implements Option<T> {
}

mut map<R>(mapper: (arg0 : T) => R): Option<R> {
	return new None<R>();
}
mut orElse(other: T): T {
	return other;
}
mut orElseGet(supplier: () => T): T {
	return supplier();
}
mut isPresent(): Bool {
	return false;
}
mut ifPresent(consumer: (arg0 : T) => void): void {
}
mut or(other: () => Option<T>): Option<T> {
	return other();
}
mut flatMap<R>(mapper: (arg0 : T) => Option<R>): Option<R> {
	return new None<R>();
}
mut filter(predicate: (arg0 : T) => boolean): Option<T> {
	return new None<T>();
}
mut toTuple(other: T): Tuple2<Bool, T> {
	return new Tuple2Impl<Bool, T>(false, other);
}
mut and<R>(other: () => Option<R>): Option<Tuple2<T, R>> {
	return new None<Tuple2<T, R>>();
}