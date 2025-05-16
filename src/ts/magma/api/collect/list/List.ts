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
import { Query } from "../../../../magma/api/collect/Query";
import { Option } from "../../../../magma/api/option/Option";
import { Tuple2 } from "../../../../magma/api/Tuple2";
export interface List<T> {
	addLast(element: T): List<T>;
	query(): Query<T>;
	size(): number;
	subList(startInclusive: number, endExclusive: number): Option<List<T>>;
	findLast(): Option<T>;
	findFirst(): Option<T>;
	find(index: number): Option<T>;
	queryWithIndices(): Query<Tuple2<number, T>>;
	addAll(others: List<T>): List<T>;
	contains(element: T, equator: (arg0 : T, arg1 : T) => boolean): boolean;
	queryReversed(): Query<T>;
	addFirst(element: T): List<T>;
	isEmpty(): boolean;
	equalsTo(other: List<T>, equator: (arg0 : T, arg1 : T) => boolean): boolean;
	removeValue(element: T, equator: (arg0 : T, arg1 : T) => boolean): List<T>;
	removeLast(): Option<List<T>>;
}
