import from 'magma.api.option.Option'.ts;
import from 'magma.app.Main'.ts;
interface List<T> {
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
}
