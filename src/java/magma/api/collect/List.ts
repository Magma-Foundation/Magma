import { Tuple2 } from "../../../magma/api/Tuple2";
import { Option } from "../../../magma/api/option/Option";
export interface List<T> {
	add(element: T): List<T>;
	query(): Query<T>;
	size(): number;
	subList(startInclusive: number, endExclusive: number): Option<List<T>>;
	findLast(): Option<T>;
	findFirst(): Option<T>;
	find(index: number): Option<T>;
	queryWithIndices(): Query<Tuple2<number, T>>;
	addAll(others: List<T>): List<T>;
	contains(element: T): boolean;
	queryReversed(): Query<T>;
	addFirst(element: T): List<T>;
	isEmpty(): boolean;
}
