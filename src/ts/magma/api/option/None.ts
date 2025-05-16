// [Actual, Namespace, Collector, EmptyHead, Head, List, ListCollector, Lists, HeadedQuery, Query, RangeHead, SingleHead, Console, IOError, Path]
import { Tuple2 } from "../../../magma/api/Tuple2";
import { Tuple2Impl } from "../../../magma/api/Tuple2Impl";
import { Main } from "../../../magma/app/Main";
import { Option } from "./Option";
export class None<T> implements Option<T> {
	map<R>(mapper: (arg0 : T) => R): Option<R> {
		return new None<R>();
	}
	orElse(other: T): T {
		return other;
	}
	orElseGet(supplier: () => T): T {
		return supplier();
	}
	isPresent(): boolean {
		return false;
	}
	ifPresent(consumer: (arg0 : T) => void): void {
	}
	or(other: () => Option<T>): Option<T> {
		return other();
	}
	flatMap<R>(mapper: (arg0 : T) => Option<R>): Option<R> {
		return new None<R>();
	}
	filter(predicate: (arg0 : T) => boolean): Option<T> {
		return new None<T>();
	}
	toTuple(other: T): Tuple2<Boolean, T> {
		return new Tuple2Impl<Boolean, T>(false, other);
	}
	and<R>(other: () => Option<R>): Option<Tuple2<T, R>> {
		return new None<Tuple2<T, R>>();
	}
}
