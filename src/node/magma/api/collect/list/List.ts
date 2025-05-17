import { Query } from "../../../../magma/api/collect/Query";
import { Option } from "../../../../magma/api/option/Option";
import { Tuple2 } from "../../../../magma/api/Tuple2";
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
	contains(element: T, equator: (arg0 : T, arg1 : T) => boolean): boolean;
	queryReversed(): Query<T>;
	addFirst(element: T): List<T>;
	isEmpty(): boolean;
	equalsTo(other: List<T>, equator: (arg0 : T, arg1 : T) => boolean): boolean;
	removeValue(element: T, equator: (arg0 : T, arg1 : T) => boolean): List<T>;
	removeLast(): Option<List<T>>;
}
