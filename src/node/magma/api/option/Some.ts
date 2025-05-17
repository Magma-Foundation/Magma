// [Lists, Lists, Lists, Console, Console, Console, Files, Files, Files, Characters, Characters, Characters, Strings, Strings, Strings, Actual, Actual, Actual, Namespace, Namespace, Namespace, Collector, Collector, Collector, EmptyHead, EmptyHead, EmptyHead, FlatMapHead, FlatMapHead, FlatMapHead, Head, Head, Head, HeadedQuery, HeadedQuery, HeadedQuery, MapHead, MapHead, MapHead, RangeHead, RangeHead, RangeHead, SingleHead, SingleHead, SingleHead, ZipHead, ZipHead, ZipHead, Joiner, Joiner, Joiner, List, List, List, ListCollector, ListCollector, ListCollector, Queries, Queries, Queries, Query, Query, Query, IOError, IOError, IOError, Path, Path, Path, None, None, None, Option, Option, Option, Some]
import { Option } from "../../../magma/api/option/Option";
import { None } from "../../../magma/api/option/None";
import { Tuple2 } from "../../../magma/api/Tuple2";
import { Tuple2Impl } from "../../../magma/api/Tuple2Impl";
export class Some<T> implements Option<T> {
	value: T;
	constructor (value: T) {
		this.value = value;
	}
	map<R>(mapper: (arg0 : T) => R): Option<R> {
		return new Some<R>(mapper/*(arg0 : T) => R*/(this/*auto*/.value));
	}
	orElse(other: T): T {
		return this/*auto*/.value;
	}
	orElseGet(supplier: () => T): T {
		return this/*auto*/.value;
	}
	isPresent(): boolean {
		return true/*auto*/;
	}
	ifPresent(consumer: (arg0 : T) => void): void {
		consumer/*(arg0 : T) => void*/(this/*auto*/.value);
	}
	or(other: () => Option<T>): Option<T> {
		return this/*auto*/;
	}
	flatMap<R>(mapper: (arg0 : T) => Option<R>): Option<R> {
		return mapper/*(arg0 : T) => Option<R>*/(this/*auto*/.value);
	}
	filter(predicate: (arg0 : T) => boolean): Option<T> {
		if (predicate/*(arg0 : T) => boolean*/(this/*auto*/.value)) {
			return this/*auto*/;
		}
		return new None<T>(/*auto*/);
	}
	toTuple(other: T): Tuple2<boolean, T> {
		return new Tuple2Impl<boolean, T>(true/*auto*/, this/*auto*/.value);
	}
	and<R>(other: () => Option<R>): Option<Tuple2<T, R>> {
		return other/*() => Option<R>*/(/*auto*/).map((otherValue: R) => new Tuple2Impl<T, R>(this/*auto*/.value, otherValue/*auto*/));
	}
}
