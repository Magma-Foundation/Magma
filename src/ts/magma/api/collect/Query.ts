/*[
	JVMList: jvm.api.collect.list, 
	JVMList: jvm.api.collect.list, 
	Lists: jvm.api.collect.list, 
	Lists: jvm.api.collect.list, 
	Files: jvm.api.io, 
	Files: jvm.api.io, 
	JVMPath: jvm.api.io, 
	JVMPath: jvm.api.io, 
	Characters: jvm.api.text, 
	Characters: jvm.api.text, 
	Strings: jvm.api.text, 
	Strings: jvm.api.text, 
	Actual: magma.annotate, 
	Actual: magma.annotate, 
	Namespace: magma.annotate, 
	Namespace: magma.annotate, 
	Collector: magma.api.collect, 
	Collector: magma.api.collect, 
	EmptyHead: magma.api.collect.head, 
	EmptyHead: magma.api.collect.head, 
	FlatMapHead: magma.api.collect.head, 
	FlatMapHead: magma.api.collect.head, 
	Head: magma.api.collect.head, 
	Head: magma.api.collect.head, 
	HeadedQuery: magma.api.collect.head, 
	HeadedQuery: magma.api.collect.head, 
	MapHead: magma.api.collect.head, 
	MapHead: magma.api.collect.head, 
	RangeHead: magma.api.collect.head, 
	RangeHead: magma.api.collect.head, 
	SingleHead: magma.api.collect.head, 
	SingleHead: magma.api.collect.head, 
	ZipHead: magma.api.collect.head, 
	ZipHead: magma.api.collect.head, 
	List: magma.api.collect.list, 
	List: magma.api.collect.list, 
	ListCollector: magma.api.collect.list, 
	ListCollector: magma.api.collect.list, 
	Queries: magma.api.collect, 
	Queries: magma.api.collect, 
	Query: magma.api.collect, 
	Query: magma.api.collect, 
	Console: magma.api.io, 
	Console: magma.api.io, 
	IOError: magma.api.io, 
	IOError: magma.api.io, 
	Path: magma.api.io, 
	Path: magma.api.io, 
	None: magma.api.option, 
	None: magma.api.option, 
	Option: magma.api.option, 
	Option: magma.api.option, 
	Some: magma.api.option, 
	Some: magma.api.option, 
	Err: magma.api.result, 
	Err: magma.api.result, 
	Ok: magma.api.result, 
	Ok: magma.api.result, 
	Result: magma.api.result, 
	Result: magma.api.result, 
	Tuple2: magma.api, 
	Tuple2: magma.api, 
	Tuple2Impl: magma.api, 
	Tuple2Impl: magma.api, 
	Main: magma.app, 
	Main: magma.app
]*/
import { Collector } from "../../../magma/api/collect/Collector";
import { Option } from "../../../magma/api/option/Option";
import { Tuple2 } from "../../../magma/api/Tuple2";
export interface Query<T> {
	collect<C>(collector: Collector<T, C>): C;
	map<R>(mapper: (arg0 : T) => R): Query<R>;
	foldWithInitial<R>(initial: R, folder: (arg0 : R, arg1 : T) => R): R;
	foldWithMapper<R>(mapper: (arg0 : T) => R, folder: (arg0 : R, arg1 : T) => R): Option<R>;
	flatMap<R>(mapper: (arg0 : T) => Query<R>): Query<R>;
	next(): Option<T>;
	allMatch(predicate: (arg0 : T) => boolean): boolean;
	filter(predicate: (arg0 : T) => boolean): Query<T>;
	anyMatch(predicate: (arg0 : T) => boolean): boolean;
	zip<R>(other: Query<R>): Query<Tuple2<T, R>>;
}
