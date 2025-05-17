#include "./Query.h"
export interface Query<T> {
	collect<C>(collector: Collector<T, C>): C;
	map<R>(mapper: (arg0 : T) => R): Query<R>;
	foldWithInitial<R>(initial: R, folder: (arg0 : R, arg1 : T) => R): R;
	foldWithMapper<R>(mapper: (arg0 : T) => R, folder: (arg0 : R, arg1 : T) => R): Option<R>;
	flatMap<R>(mapper: (arg0 : T) => Query<R>): Query<R>;
	next(): Option<T>;
	allMatch(predicate: (arg0 : T) => boolean): Bool;
	filter(predicate: (arg0 : T) => boolean): Query<T>;
	anyMatch(predicate: (arg0 : T) => boolean): Bool;
	zip<R>(other: Query<R>): Query<Tuple2<T, R>>;
}
