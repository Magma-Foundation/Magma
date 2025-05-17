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
auto temp(C current, T element) {
	return collector.fold(current, element);
}
C collect(Collector<T, C> collector) {
	return this.foldWithInitial(collector.createInitial(), temp);
}
Query<R> map((arg0 : T) => R mapper) {
	return new HeadedQuery<R>(new MapHead<T, R>(this.head, mapper));
}
auto temp(T inner) {
	return folder(finalResult, inner);
}
R foldWithInitial(R initial, (arg0 : R, arg1 : T) => R folder) {
	R result = initial;
	while (true) {
		R finalResult = result;
		Tuple2<Bool, R> maybeNext = this.head.next().map(temp).toTuple(finalResult);
		if (maybeNext.left()) {
			result = maybeNext.right();
		}
		else {
			return result;
		}
	}
}
auto temp(R maybeNext) {
	return this.foldWithInitial(maybeNext, folder);
}
Option<R> foldWithMapper((arg0 : T) => R next, (arg0 : R, arg1 : T) => R folder) {
	return this.head.next().map(next).map(temp);
}
auto temp(Query<R> initial) {
	return new HeadedQuery<R>(new FlatMapHead<T, R>(this.head, initial, mapper));
}
auto temp() {
	return new HeadedQuery<R>(new EmptyHead<R>());
}
Query<R> flatMap((arg0 : T) => Query<R> mapper) {
	return this.head.next().map(mapper).map(temp).orElseGet(temp);
}
auto temp(Bool maybeAllTrue, T element) {
	return maybeAllTrue && predicate(element);
}
Bool allMatch((arg0 : T) => boolean predicate) {
	return this.foldWithInitial(true, temp);
}
auto temp(Bool aBoolean, T t) {
	return aBoolean || predicate(t);
}
Bool anyMatch((arg0 : T) => boolean predicate) {
	return this.foldWithInitial(false, temp);
}
Query<Tuple2<T, R>> zip(Query<R> other) {
	return new HeadedQuery<Tuple2<T, R>>(new ZipHead<T, R>(this.head, other));
}
auto temp(T element) {{
		if (predicate(element)) {
			return new HeadedQuery<T>(new SingleHead<T>(element));
		}
		else {
			return new HeadedQuery<T>(new EmptyHead<T>());
		}
	}
}
Query<T> filter((arg0 : T) => boolean predicate) {
	return this.flatMap(temp);
}