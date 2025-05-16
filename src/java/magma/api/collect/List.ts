import { Option } from "../../../magma/api/option/Option";
import { Main } from "../../../magma/app/Main";
export interface List<T> {
	add(element: T): List<T>;
	query(): Main.Query<T>;
	size(): number;
	subList(startInclusive: number, endExclusive: number): Option<List<T>>;
	findLast(): Option<T>;
	findFirst(): Option<T>;
	find(index: number): Option<T>;
	queryWithIndices(): Main.Query<Main.Tuple<number, T>>;
	addAll(others: List<T>): List<T>;
	contains(element: T): boolean;
	queryReversed(): Main.Query<T>;
	addFirst(element: T): List<T>;
	isEmpty(): boolean;
}
