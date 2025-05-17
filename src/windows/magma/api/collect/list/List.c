#include "./List.h"
export interface List<T> {
	addLast(element: T): List<T>;
	query(): Query<T>;
	size(): number;
	subList(startInclusive: number, endExclusive: number): Option<List<T>>;
	findLast(): Option<T>;
	findFirst(): Option<T>;
	find(index: number): Option<T>;
	queryWithIndices(): Query<Tuple2<number, T>>;
	addAll(others: List<T>): List<T>;
	contains(element: T, equator: (arg0 : T, arg1 : T) => Bool): Bool;
	queryReversed(): Query<T>;
	addFirst(element: T): List<T>;
	isEmpty(): Bool;
	equalsTo(other: List<T>, equator: (arg0 : T, arg1 : T) => Bool): Bool;
	removeValue(element: T, equator: (arg0 : T, arg1 : T) => Bool): List<T>;
	removeLast(): Option<List<T>>;
}
