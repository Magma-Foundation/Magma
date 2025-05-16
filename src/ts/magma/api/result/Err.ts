/*[
	JVMList: jvm.api.collect.list, 
	Lists: jvm.api.collect.list, 
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
	List: magma.api.collect.list, 
	ListCollector: magma.api.collect.list, 
	Queries: magma.api.collect, 
	Query: magma.api.collect, 
	Console: magma.api.io, 
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
import { Result } from "../../../magma/api/result/Result";
import { Option } from "../../../magma/api/option/Option";
import { Some } from "../../../magma/api/option/Some";
import { None } from "../../../magma/api/option/None";
export class Err<T, X> implements Result<T, X> {
	error: X;
	constructor (error: X) {
		this.error = error;
	}
	findError(): Option<X> {
		return new Some<>(this.error);
	}
	findValue(): Option<T> {
		return new None<>();
	}
	match<R>(whenOk: (arg0 : T) => R, whenErr: (arg0 : X) => R): R {
		return whenErr(this.error);
	}
}
