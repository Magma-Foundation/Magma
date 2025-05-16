/*[
	Actual: magma.annotate, 
	Namespace: magma.annotate, 
	Collector: magma.api.collect, 
	EmptyHead: magma.api.collect, 
	Head: magma.api.collect, 
	JVMList: magma.api.collect, 
	List: magma.api.collect, 
	ListCollector: magma.api.collect, 
	Lists: magma.api.collect, 
	HeadedQuery: magma.api.collect.query, 
	Query: magma.api.collect.query, 
	RangeHead: magma.api.collect, 
	SingleHead: magma.api.collect, 
	Console: magma.api, 
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
	Main: magma.app, 
	Files: magma.jvm.io
]*/
import { Tuple2 } from "../../../magma/api/Tuple2";
import { Tuple2Impl } from "../../../magma/api/Tuple2Impl";
import { Main } from "../../../magma/app/Main";
import { Option } from "../../../magma/api/option/Option";
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
