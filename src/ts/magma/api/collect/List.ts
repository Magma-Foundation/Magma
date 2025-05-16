import { Tuple2 } from "../../../magma/api/Tuple2";
import { Query } from "../../../magma/api/collect/query/Query";
import { Option } from "../../../magma/api/option/Option";
import { List } from "./List";
import { Query } from "./Query";
import { Option } from "./Option";
import { Tuple2 } from "./Tuple2";
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
	contains(element: T): boolean;
	queryReversed(): Query<T>;
	addFirst(element: T): List<T>;
	isEmpty(): boolean;
	equalsTo(other: List<T>): boolean;
	removeValue(element: T): List<T>;
}
