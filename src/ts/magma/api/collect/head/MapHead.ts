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
import { Head } from "../../../../magma/api/collect/head/Head";
import { Option } from "../../../../magma/api/option/Option";
export class MapHead<T, R> implements Head<R> {
	head: Head<T>;
	mapper: (arg0 : T) => R;
	constructor (head: Head<T>, mapper: (arg0 : T) => R) {
		this.head = head;
		this.mapper = mapper;
	}
	next(): Option<R> {
		return this.head.next().map(this.mapper);
	}
}
