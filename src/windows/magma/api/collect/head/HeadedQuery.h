import { Query } from "magma/api/collect/Query";
import { Head } from "magma/api/collect/head/Head";
import { Option } from "magma/api/option/Option";
import { Collector } from "magma/api/collect/Collector";
import { MapHead } from "magma/api/collect/head/MapHead";
import { Tuple2 } from "magma/api/Tuple2";
import { FlatMapHead } from "magma/api/collect/head/FlatMapHead";
import { EmptyHead } from "magma/api/collect/head/EmptyHead";
import { ZipHead } from "magma/api/collect/head/ZipHead";
import { SingleHead } from "magma/api/collect/head/SingleHead";
export class HeadedQuery<T> implements Query<T> {
	mut head: Head<T>;
	constructor (mut head: Head<T>) {
		this.head = head;
	}
	mut next(): Option<T> {
		return this.head.next();
	}
	mut collect<C>(collector: Collector<T, C>): C {
		return this.foldWithInitial(collector.createInitial(), (mut current: C, mut element: T) => collector.fold(current, element));
	}
	mut map<R>(mapper: (arg0 : T) => R): Query<R> {
		return new HeadedQuery<R>(new MapHead<T, R>(this.head, mapper));
	}
	mut foldWithInitial<R>(initial: R, folder: (arg0 : R, arg1 : T) => R): R {
		let mut result: R = initial;
		while (true) {
			let finalResult: R = result;
			let maybeNext: Tuple2<Bool, R> = this.head.next().map((mut inner: T) => folder(finalResult, inner)).toTuple(finalResult);
			if (maybeNext.left()) {
				result = maybeNext.right();
			}
			else {
				return result;
			}
		}
	}
	mut foldWithMapper<R>(next: (arg0 : T) => R, folder: (arg0 : R, arg1 : T) => R): Option<R> {
		return this.head.next().map(next).map((maybeNext: R) => this.foldWithInitial(maybeNext, folder));
	}
	mut flatMap<R>(mapper: (arg0 : T) => Query<R>): Query<R> {
		return this.head.next().map(mapper).map((mut initial: Query<R>) => new HeadedQuery<R>(new FlatMapHead<T, R>(this.head, initial, mapper))).orElseGet(() => new HeadedQuery<R>(new EmptyHead<R>()));
	}
	mut allMatch(predicate: (arg0 : T) => boolean): Bool {
		return this.foldWithInitial(true, (mut maybeAllTrue: Bool, mut element: T) => maybeAllTrue && predicate(element));
	}
	mut anyMatch(predicate: (arg0 : T) => boolean): Bool {
		return this.foldWithInitial(false, (mut aBoolean: Bool, mut t: T) => aBoolean || predicate(t));
	}
	mut zip<R>(other: Query<R>): Query<Tuple2<T, R>> {
		return new HeadedQuery<Tuple2<T, R>>(new ZipHead<T, R>(this.head, other));
	}
	mut filter(predicate: (arg0 : T) => boolean): Query<T> {
		return this.flatMap((element: T) => {
			if (predicate(element)) {
				return new HeadedQuery<T>(new SingleHead<T>(element));
			}
			else {
				return new HeadedQuery<T>(new EmptyHead<T>());
			}
		});
	}
}
