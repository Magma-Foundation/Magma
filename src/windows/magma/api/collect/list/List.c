#include "./List.h"
export interface List<T> {
	mut addLast(mut element: T): List<T>;
	mut query(): Query<T>;
	mut size(): number;
	mut subList(mut startInclusive: number, mut endExclusive: number): Option<List<T>>;
	mut findLast(): Option<T>;
	mut findFirst(): Option<T>;
	mut find(mut index: number): Option<T>;
	mut queryWithIndices(): Query<Tuple2<number, T>>;
	mut addAll(mut others: List<T>): List<T>;
	mut contains(mut element: T, mut equator: (arg0 : T, arg1 : T) => Bool): Bool;
	mut queryReversed(): Query<T>;
	mut addFirst(mut element: T): List<T>;
	mut isEmpty(): Bool;
	mut equalsTo(mut other: List<T>, mut equator: (arg0 : T, arg1 : T) => Bool): Bool;
	mut removeValue(mut element: T, mut equator: (arg0 : T, arg1 : T) => Bool): List<T>;
	mut removeLast(): Option<List<T>>;
}
