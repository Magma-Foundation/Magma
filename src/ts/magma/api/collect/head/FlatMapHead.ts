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
import { Query } from "../../../../magma/api/collect/Query";
import { Option } from "../../../../magma/api/option/Option";
import { None } from "../../../../magma/api/option/None";
export class FlatMapHead<T, R> implements Head<R> {
	mapper: (arg0 : T) => Query<R>;
	head: Head<T>;
	current: Query<R>;
	constructor (head: Head<T>, initial: Query<R>, mapper: (arg0 : T) => Query<R>) {
		this.head = head;
		this.current = initial;
		this.mapper = mapper;
	}
	next(): Option<R> {
		while (true){
			let next = this.current.next();
			if (next.isPresent()){
				return next;
			}
			let tuple = this.head.next().map(this.mapper).toTuple(this.current);
			if (tuple.left()){
				this.current = tuple.right();
			}
			else {
				return new None<R>();
			}
		}
	}
}
