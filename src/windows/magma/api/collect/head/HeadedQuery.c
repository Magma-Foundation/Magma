#include "./HeadedQuery.h"
export class HeadedQuery<T> implements Query<T> {
	Head<T> head;
	constructor (Head<T> head) {
		this.head = head;
	}
}

Option<T> next() {
	return this.head.next();
}
C collect(Collector<T, C> collector) {
	return this.foldWithInitial(collector.createInitial(), (C current, T element) => collector.fold(current, element));
}
Query<R> map((arg0 : T) => R mapper) {
	return new HeadedQuery<R>(new MapHead<T, R>(this.head, mapper));
}
R foldWithInitial(R initial, (arg0 : R, arg1 : T) => R folder) {
	R result = initial;
	while (true) {
		R finalResult = result;
		Tuple2<Bool, R> maybeNext = this.head.next().map((T inner) => folder(finalResult, inner)).toTuple(finalResult);
		if (maybeNext.left()) {
			result = maybeNext.right();
		}
		else {
			return result;
		}
	}
}
Option<R> foldWithMapper((arg0 : T) => R next, (arg0 : R, arg1 : T) => R folder) {
	return this.head.next().map(next).map((R maybeNext) => this.foldWithInitial(maybeNext, folder));
}
Query<R> flatMap((arg0 : T) => Query<R> mapper) {
	return this.head.next().map(mapper).map((Query<R> initial) => new HeadedQuery<R>(new FlatMapHead<T, R>(this.head, initial, mapper))).orElseGet(() => new HeadedQuery<R>(new EmptyHead<R>()));
}
Bool allMatch((arg0 : T) => boolean predicate) {
	return this.foldWithInitial(true, (Bool maybeAllTrue, T element) => maybeAllTrue && predicate(element));
}
Bool anyMatch((arg0 : T) => boolean predicate) {
	return this.foldWithInitial(false, (Bool aBoolean, T t) => aBoolean || predicate(t));
}
Query<Tuple2<T, R>> zip(Query<R> other) {
	return new HeadedQuery<Tuple2<T, R>>(new ZipHead<T, R>(this.head, other));
}
Query<T> filter((arg0 : T) => boolean predicate) {
	return this.flatMap((T element) => {
		if (predicate(element)) {
			return new HeadedQuery<T>(new SingleHead<T>(element));
		}
		else {
			return new HeadedQuery<T>(new EmptyHead<T>());
		}
	});
}