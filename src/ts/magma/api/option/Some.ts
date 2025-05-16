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
		return new Some<R>(mapper(this.value));
	}
	orElse(other: T): T {
		return this.value;
	}
	orElseGet(supplier: () => T): T {
		return this.value;
	}
	isPresent(): boolean {
		return true;
	}
	ifPresent(consumer: (arg0 : T) => void): void {
		consumer(this.value);
	}
	or(other: () => Option<T>): Option<T> {
		return this;
	}
	flatMap<R>(mapper: (arg0 : T) => Option<R>): Option<R> {
		return mapper(this.value);
	}
	filter(predicate: (arg0 : T) => boolean): Option<T> {
		if (predicate(this.value)){
			return this;
		}
		return new None<T>();
	}
	toTuple(other: T): Tuple2<boolean, T> {
		return new Tuple2Impl<boolean, T>(true, this.value);
	}
	and<R>(other: () => Option<R>): Option<Tuple2<T, R>> {
		return other().map((otherValue: R) => new Tuple2Impl<T, R>(this.value, otherValue));
	}
}
