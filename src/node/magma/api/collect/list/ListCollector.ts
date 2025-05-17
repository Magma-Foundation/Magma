// []
import { List } from "../../../../magma/api/collect/list/List";
import { Collector } from "../../../../magma/api/collect/Collector";
import { Lists } from "../../../../jvm/api/collect/list/Lists";
export class ListCollector<T> implements Collector<T, List<T>> {
	createInitial(): List<T> {
		return Lists/*auto*/.empty(/*auto*/);
	}
	fold(current: List<T>, element: T): List<T> {
		return current/*List<T>*/.addLast(element/*T*/);
	}
}
