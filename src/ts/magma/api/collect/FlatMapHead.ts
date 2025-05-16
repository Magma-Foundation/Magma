/*[
	Actual: magma.annotate, 
	Namespace: magma.annotate, 
	Collector: magma.api.collect, 
	EmptyHead: magma.api.collect, 
	FlatMapHead: magma.api.collect, 
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
import { Head } from "../../../magma/api/collect/Head";
import { Query } from "../../../magma/api/collect/query/Query";
import { Option } from "../../../magma/api/option/Option";
import { None } from "../../../magma/api/option/None";
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
