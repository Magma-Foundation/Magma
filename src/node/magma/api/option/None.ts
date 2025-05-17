// [Lists, Lists, Lists, Console, Console, Console, Files, Files, Files, Characters, Characters, Characters, Strings, Strings, Strings, Actual, Actual, Actual, Namespace, Namespace, Namespace, Collector, Collector, Collector, EmptyHead, EmptyHead, EmptyHead, FlatMapHead, FlatMapHead, FlatMapHead, Head, Head, Head, HeadedQuery, HeadedQuery, HeadedQuery, MapHead, MapHead, MapHead, RangeHead, RangeHead, RangeHead, SingleHead, SingleHead, SingleHead, ZipHead, ZipHead, ZipHead, Joiner, Joiner, Joiner, List, List, List, ListCollector, ListCollector, ListCollector, Queries, Queries, Queries, Query, Query, Query, IOError, IOError, IOError, Path, Path, Path, None]
import { Option } from "../../../magma/api/option/Option";
import { Tuple2 } from "../../../magma/api/Tuple2";
import { Tuple2Impl } from "../../../magma/api/Tuple2Impl";
export class None<T> implements Option<T> {
	map<R>(mapper: (arg0 : T) => R): Option<R> {
		return new None<R>(/*auto*/);
	}
	orElse(other: T): T {
		return other/*T*/;
	}
	orElseGet(supplier: () => T): T {
		return supplier/*() => T*/(/*auto*/);
	}
	isPresent(): boolean {
		return false/*auto*/;
	}
	ifPresent(consumer: (arg0 : T) => void): void {
	}
	or(other: () => Option<T>): Option<T> {
		return other/*() => Option<T>*/(/*auto*/);
	}
	flatMap<R>(mapper: (arg0 : T) => Option<R>): Option<R> {
		return new None<R>(/*auto*/);
	}
	filter(predicate: (arg0 : T) => boolean): Option<T> {
		return new None<T>(/*auto*/);
	}
	toTuple(other: T): Tuple2<boolean, T> {
		return new Tuple2Impl<boolean, T>(false/*auto*/, other/*T*/);
	}
	and<R>(other: () => Option<R>): Option<Tuple2<T, R>> {
		return new None<Tuple2<T, R>>(/*auto*/);
	}
}
