import { List } from "./List";
import { Collector } from "./Collector";
export class ListCollector<T> implements Collector<T, List<T>> {
	createInitial(): List<T> {
		return Lists.empty();
	}
	fold(current: List<T>, element: T): List<T> {
		return current.addLast(element);
	}
}
