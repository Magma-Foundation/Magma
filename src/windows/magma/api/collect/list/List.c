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
	Definition: magma.app, 
	Main: magma.app, 
	MethodHeader: magma.app, 
	Parameter: magma.app
]*/
import { Query } from "magma/api/collect/Query";
import { Option } from "magma/api/option/Option";
import { Tuple2 } from "magma/api/Tuple2";
export interface List<T> {
	mut addLast(mut element: T): List<T>;
	mut query(): Query<T>;
	mut size(): number;
	mut subList(mut startInclusive: number, mut endExclusive: number): Option<List<T>>;
	mut findLast(): Option<T>;
	mut findFirst(): Option<T>;
	mut find(mut index: number): Option<T>;
	mut queryWithIndices(): Query<Tuple2<number, T>>;
	mut addAll(mut others: List<T>): List<T>;
	mut contains(mut element: T, mut equator: (arg0 : T, arg1 : T) => Bool): Bool;
	mut queryReversed(): Query<T>;
	mut addFirst(mut element: T): List<T>;
	mut isEmpty(): Bool;
	mut equalsTo(mut other: List<T>, mut equator: (arg0 : T, arg1 : T) => Bool): Bool;
	mut removeValue(mut element: T, mut equator: (arg0 : T, arg1 : T) => Bool): List<T>;
	mut removeLast(): Option<List<T>>;
}
