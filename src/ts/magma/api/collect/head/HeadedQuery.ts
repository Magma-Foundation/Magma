/*[
	JVMList: jvm.api.collect.list, 
	Lists: jvm.api.collect.list, 
	Files: jvm.api.io, 
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
	List: magma.api.collect.list, 
	ListCollector: magma.api.collect.list, 
	Query: magma.api.collect, 
	Console: magma.api.io, 
	IOError: magma.api.io, 
	Path: magma.api.io, 
	None: magma.api.option, 
	Option: magma.api.option, 
	Some: magma.api.option, 
	Result: magma.api.result, 
	Characters: magma.api.text, 
	Strings: magma.api.text, 
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
		return this.foldWithInitial(collector.createInitial(), collector.fold);
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
		return this.head.next().map(next).map((maybeNext: R) => {
			return this.foldWithInitial(maybeNext, folder);
		});
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
