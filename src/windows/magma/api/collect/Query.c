#include "./Query.h"
export interface Query<T> {
	C collect(Collector<T, C> collector);
	Query<R> map((arg0 : T) => R mapper);
	R foldWithInitial(R initial, (arg0 : R, arg1 : T) => R folder);
	Option<R> foldWithMapper((arg0 : T) => R mapper, (arg0 : R, arg1 : T) => R folder);
	Query<R> flatMap((arg0 : T) => Query<R> mapper);
	Option<T> next();
	Bool allMatch((arg0 : T) => boolean predicate);
	Query<T> filter((arg0 : T) => boolean predicate);
	Bool anyMatch((arg0 : T) => boolean predicate);
	Query<Tuple2<T, R>> zip(Query<R> other);
	Result<R, X> foldWithInitialToResult(R initial, (arg0 : R, arg1 : T) => Result<R, X> mapper);
}
