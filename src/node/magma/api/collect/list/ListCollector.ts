// [Lists, Lists, Lists, Console, Console, Console, Files, Files, Files, Characters, Characters, Characters, Strings, Strings, Strings, Actual, Actual, Actual, Namespace, Namespace, Namespace, Collector, Collector, Collector, EmptyHead, EmptyHead, EmptyHead, FlatMapHead, FlatMapHead, FlatMapHead, Head, Head, Head, HeadedQuery, HeadedQuery, HeadedQuery, MapHead, MapHead, MapHead, RangeHead, RangeHead, RangeHead, SingleHead, SingleHead, SingleHead, ZipHead, ZipHead, ZipHead, Joiner, Joiner, Joiner, List, List, List, ListCollector]
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
