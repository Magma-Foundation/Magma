#include "./HeadedQuery.h"
export class HeadedQuery<T> implements Query<T> {
	Head<T> head;
	constructor (Head<T> head) {
		this.head = head;
	}
}

Option<T> next() {
	return this/*auto*/.head.next(/*auto*/);
}
auto temp(C current, T element) {
	return collector/*auto*/.fold(current/*auto*/, element/*auto*/);
}
C collect(Collector<T, C> collector) {
	return this/*auto*/.foldWithInitial(collector/*auto*/.createInitial(/*auto*/), lambdaDefinition/*auto*/);
}
Query<R> map((arg0 : T) => R mapper) {
	return new HeadedQuery<R>(new MapHead<T, R>(this/*auto*/.head, mapper/*auto*/));
}
auto temp(T inner) {
	return folder/*auto*/(finalResult/*auto*/, inner/*auto*/);
}
R foldWithInitial(R initial, (arg0 : R, arg1 : T) => R folder) {
	R result = initial/*auto*/;
	while (true/*auto*/) {
		R finalResult = result/*auto*/;
		Tuple2<Bool, R> maybeNext = this/*auto*/.head.next(/*auto*/).map(lambdaDefinition/*auto*/).toTuple(finalResult/*auto*/);
		if (maybeNext/*auto*/.left(/*auto*/)) {
			result/*auto*/ = maybeNext/*auto*/.right(/*auto*/);
		}
		else {
			return result/*auto*/;
		}
	}
}
auto temp(R maybeNext) {
	return this/*auto*/.foldWithInitial(maybeNext/*auto*/, folder/*auto*/);
}
Option<R> foldWithMapper((arg0 : T) => R next, (arg0 : R, arg1 : T) => R folder) {
	return this/*auto*/.head.next(/*auto*/).map(next/*auto*/).map(lambdaDefinition/*auto*/);
}
auto temp(Query<R> initial) {
	return new HeadedQuery<R>(new FlatMapHead<T, R>(this/*auto*/.head, initial/*auto*/, mapper/*auto*/));
}
auto temp() {
	return new HeadedQuery<R>(new EmptyHead<R>(/*auto*/));
}
Query<R> flatMap((arg0 : T) => Query<R> mapper) {
	return this/*auto*/.head.next(/*auto*/).map(mapper/*auto*/).map(lambdaDefinition/*auto*/).orElseGet(lambdaDefinition/*auto*/);
}
auto temp(Bool maybeAllTrue, T element) {
	return maybeAllTrue/*auto*/ && predicate/*auto*/(element/*auto*/);
}
Bool allMatch((arg0 : T) => boolean predicate) {
	return this/*auto*/.foldWithInitial(true/*auto*/, lambdaDefinition/*auto*/);
}
auto temp(Bool aBoolean, T t) {
	return aBoolean/*auto*/ || predicate/*auto*/(t/*auto*/);
}
Bool anyMatch((arg0 : T) => boolean predicate) {
	return this/*auto*/.foldWithInitial(false/*auto*/, lambdaDefinition/*auto*/);
}
Query<Tuple2<T, R>> zip(Query<R> other) {
	return new HeadedQuery<Tuple2<T, R>>(new ZipHead<T, R>(this/*auto*/.head, other/*auto*/));
}
auto temp(T element) {{
		if (predicate/*auto*/(element/*auto*/)) {
			return new HeadedQuery<T>(new SingleHead<T>(element/*auto*/));
		}
		else {
			return new HeadedQuery<T>(new EmptyHead<T>(/*auto*/));
		}
	}
}
Query<T> filter((arg0 : T) => boolean predicate) {
	return this/*auto*/.flatMap(lambdaDefinition/*auto*/);
}