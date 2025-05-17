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
	return collector/*Collector<T, C>*/.fold(current/*auto*/, element/*T*/);
}
C collect(Collector<T, C> collector) {
	return this/*auto*/.foldWithInitial(collector/*Collector<T, C>*/.createInitial(/*auto*/), lambdaDefinition/*auto*/);
}
Query<R> map((arg0 : T) => R mapper) {
	return new HeadedQuery<R>(new MapHead<T, R>(this/*auto*/.head, mapper/*(arg0 : T) => R*/));
}
auto temp(T inner) {
	return folder/*(arg0 : R, arg1 : T) => R*/(finalResult/*auto*/, inner/*auto*/);
}
R foldWithInitial(R initial, (arg0 : R, arg1 : T) => R folder) {
	R result = initial/*R*/;
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
	return this/*auto*/.foldWithInitial(maybeNext/*auto*/, folder/*(arg0 : R, arg1 : T) => R*/);
}
Option<R> foldWithMapper((arg0 : T) => R next, (arg0 : R, arg1 : T) => R folder) {
	return this/*auto*/.head.next(/*auto*/).map(next/*(arg0 : T) => R*/).map(lambdaDefinition/*auto*/);
}
auto temp(Query<R> initial) {
	return new HeadedQuery<R>(new FlatMapHead<T, R>(this/*auto*/.head, initial/*R*/, mapper/*(arg0 : T) => Query<R>*/));
}
auto temp() {
	return new HeadedQuery<R>(new EmptyHead<R>(/*auto*/));
}
Query<R> flatMap((arg0 : T) => Query<R> mapper) {
	return this/*auto*/.head.next(/*auto*/).map(mapper/*(arg0 : T) => Query<R>*/).map(lambdaDefinition/*auto*/).orElseGet(lambdaDefinition/*auto*/);
}
auto temp(Bool maybeAllTrue, T element) {
	return maybeAllTrue/*auto*/ && predicate/*(arg0 : T) => boolean*/(element/*T*/);
}
Bool allMatch((arg0 : T) => boolean predicate) {
	return this/*auto*/.foldWithInitial(true/*auto*/, lambdaDefinition/*auto*/);
}
auto temp(Bool aBoolean, T t) {
	return aBoolean/*auto*/ || predicate/*(arg0 : T) => boolean*/(t/*auto*/);
}
Bool anyMatch((arg0 : T) => boolean predicate) {
	return this/*auto*/.foldWithInitial(false/*auto*/, lambdaDefinition/*auto*/);
}
Query<Tuple2<T, R>> zip(Query<R> other) {
	return new HeadedQuery<Tuple2<T, R>>(new ZipHead<T, R>(this/*auto*/.head, other/*Query<R>*/));
}
auto temp(T element) {{
		if (predicate/*(arg0 : T) => boolean*/(element/*T*/)) {
			return new HeadedQuery<T>(new SingleHead<T>(element/*T*/));
		}
		else {
			return new HeadedQuery<T>(new EmptyHead<T>(/*auto*/));
		}
	}
}
Query<T> filter((arg0 : T) => boolean predicate) {
	return this/*auto*/.flatMap(lambdaDefinition/*auto*/);
}