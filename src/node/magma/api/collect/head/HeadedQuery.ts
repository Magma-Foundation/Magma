/*[
	JVMList: jvm.api.collect.list, 
	Lists: jvm.api.collect.list, 
	Console: jvm.api.io, 
	Files: jvm.api.io, 
	JVMPath: jvm.api.io, 
	Characters: jvm.api.text, 
	Strings: jvm.api.text, 
	Actual: magma.annotate, 
	Namespace: magma.annotate, 
	Collector: magma.api.collect, 
	EmptyHead: magma.api.collect.head, 
	FlatMapHead: magma.api.collect.head, 
	Head: magma.api.collect.head, 
	HeadedQuery: magma.api.collect.head, 
	MapHead: magma.api.collect.head, 
	RangeHead: magma.api.collect.head, 
	SingleHead: magma.api.collect.head, 
	ZipHead: magma.api.collect.head, 
	List: magma.api.collect.list, 
	ListCollector: magma.api.collect.list, 
	Queries: magma.api.collect, 
	Query: magma.api.collect, 
	IOError: magma.api.io, 
	Path: magma.api.io, 
	None: magma.api.option, 
	Option: magma.api.option, 
	Some: magma.api.option, 
	Err: magma.api.result, 
	Ok: magma.api.result, 
	Result: magma.api.result, 
	Tuple2: magma.api, 
	Tuple2Impl: magma.api, 
	Main: magma.app
]*/
import { Query } from "../../../../magma/api/collect/Query";
import { Head } from "../../../../magma/api/collect/head/Head";
import { Option } from "../../../../magma/api/option/Option";
import { Collector } from "../../../../magma/api/collect/Collector";
import { MapHead } from "../../../../magma/api/collect/head/MapHead";
import { Tuple2 } from "../../../../magma/api/Tuple2";
import { FlatMapHead } from "../../../../magma/api/collect/head/FlatMapHead";
import { EmptyHead } from "../../../../magma/api/collect/head/EmptyHead";
import { ZipHead } from "../../../../magma/api/collect/head/ZipHead";
import { SingleHead } from "../../../../magma/api/collect/head/SingleHead";
export class HeadedQuery<T> implements Query<T> {
	head: Head<T>;
	constructor (head: Head<T>) {
		this.head = head;
	}
	next(): Option<T> {
		return this.head.next();
	}
	collect<C>(collector: Collector<T, C>): C {
		return this.foldWithInitial(collector.createInitial(), (current: C, element: T) => collector.fold(current, element));
	}
	map<R>(mapper: (arg0 : T) => R): Query<R> {
		return new HeadedQuery<R>(new MapHead<T, R>(this.head, mapper));
	}
	foldWithInitial<R>(initial: R, folder: (arg0 : R, arg1 : T) => R): R {
		let result: R = initial;
		while (true){
			let finalResult: R = result;
			let maybeNext: Tuple2<boolean, R> = this.head.next().map((inner: T) => folder(finalResult, inner)).toTuple(finalResult);
			if (maybeNext.left()){
				result = maybeNext.right();
			}
			else {
				return result;
			}
		}
	}
	foldWithMapper<R>(next: (arg0 : T) => R, folder: (arg0 : R, arg1 : T) => R): Option<R> {
		return this.head.next().map(next).map((maybeNext: R) => this.foldWithInitial(maybeNext, folder));
	}
	flatMap<R>(mapper: (arg0 : T) => Query<R>): Query<R> {
		return this.head.next().map(mapper).map((initial: Query<R>) => new HeadedQuery<R>(new FlatMapHead<T, R>(this.head, initial, mapper))).orElseGet(() => new HeadedQuery<R>(new EmptyHead<R>()));
	}
	allMatch(predicate: (arg0 : T) => boolean): boolean {
		return this.foldWithInitial(true, (maybeAllTrue: boolean, element: T) => maybeAllTrue && predicate(element));
	}
	anyMatch(predicate: (arg0 : T) => boolean): boolean {
		return this.foldWithInitial(false, (aBoolean: boolean, t: T) => aBoolean || predicate(t));
	}
	zip<R>(other: Query<R>): Query<Tuple2<T, R>> {
		return new HeadedQuery<Tuple2<T, R>>(new ZipHead<T, R>(this.head, other));
	}
	filter(predicate: (arg0 : T) => boolean): Query<T> {
		return this.flatMap((element: T) => {
			if (predicate(element)){
				return new HeadedQuery<T>(new SingleHead<T>(element));
			}
			else {
				return new HeadedQuery<T>(new EmptyHead<T>());
			}
		});
	}
}
