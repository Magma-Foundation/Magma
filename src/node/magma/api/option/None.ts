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
	Joiner: magma.api.collect, 
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
	Type: magma.api, 
	Definition: magma.app, 
	Main: magma.app, 
	MethodHeader: magma.app, 
	Parameter: magma.app
]*/
import { Option } from "../../../magma/api/option/Option";
import { Tuple2 } from "../../../magma/api/Tuple2";
import { Tuple2Impl } from "../../../magma/api/Tuple2Impl";
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
	toTuple(other: T): Tuple2<boolean, T> {
		return new Tuple2Impl<boolean, T>(false, other);
	}
	and<R>(other: () => Option<R>): Option<Tuple2<T, R>> {
		return new None<Tuple2<T, R>>();
	}
}
