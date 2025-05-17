#include "./Some.h"
export class Some<T> implements Option<T> {
	mut value: T;
	constructor (mut value: T) {
		this.value = value;
	}
	mut map<R>(mapper: (arg0 : T) => R): Option<R> {
		return new Some<R>(mapper(this.value));
	}
	mut orElse(other: T): T {
		return this.value;
	}
	mut orElseGet(supplier: () => T): T {
		return this.value;
	}
	mut isPresent(): Bool {
		return true;
	}
	mut ifPresent(consumer: (arg0 : T) => void): void {
		consumer(this.value);
	}
	mut or(other: () => Option<T>): Option<T> {
		return this;
	}
	mut flatMap<R>(mapper: (arg0 : T) => Option<R>): Option<R> {
		return mapper(this.value);
	}
	mut filter(predicate: (arg0 : T) => boolean): Option<T> {
		if (predicate(this.value)) {
			return this;
		}
		return new None<T>();
	}
	mut toTuple(other: T): Tuple2<Bool, T> {
		return new Tuple2Impl<Bool, T>(true, this.value);
	}
	mut and<R>(other: () => Option<R>): Option<Tuple2<T, R>> {
		return other().map((mut otherValue: R) => new Tuple2Impl<T, R>(this.value, otherValue));
	}
}
