// [Lists, Lists, Lists, Console, Console, Console, Files, Files, Files, Characters, Characters, Characters, Strings, Strings, Strings, Actual, Actual, Actual, Namespace, Namespace, Namespace, Collector, Collector, Collector, EmptyHead, EmptyHead, EmptyHead, FlatMapHead, FlatMapHead, FlatMapHead, Head, Head, Head, HeadedQuery, HeadedQuery, HeadedQuery, MapHead, MapHead, MapHead, RangeHead, RangeHead, RangeHead, SingleHead, SingleHead, SingleHead, ZipHead]
import { Tuple2 } from "../../../../magma/api/Tuple2";
import { Head } from "../../../../magma/api/collect/head/Head";
import { Query } from "../../../../magma/api/collect/Query";
import { Option } from "../../../../magma/api/option/Option";
export class ZipHead<T, R> implements Head<Tuple2<T, R>> {
	head: Head<T>;
	other: Query<R>;
	constructor (head: Head<T>, other: Query<R>) {
		this.head = head;
		this.other = other;
	}
	next(): Option<Tuple2<T, R>> {
		return this/*auto*/.head.next(/*auto*/).and(() => this/*auto*/.other.next(/*auto*/));
	}
}
