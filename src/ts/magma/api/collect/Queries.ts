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
import { Query } from "../../../magma/api/collect/Query";
import { Option } from "../../../magma/api/option/Option";
import { HeadedQuery } from "../../../magma/api/collect/head/HeadedQuery";
import { EmptyHead } from "../../../magma/api/collect/head/EmptyHead";
import { Head } from "../../../magma/api/collect/head/Head";
import { SingleHead } from "../../../magma/api/collect/head/SingleHead";
export class Queries {
	static fromOption<T>(option: Option<T>): Query<T> {
		return new HeadedQuery<T>(option.map((element: T) => Queries.getTSingleHead(element)).orElseGet(() => new EmptyHead<T>()));
	}
	static getTSingleHead<T>(element: T): Head<T> {
		return new SingleHead<T>(element);
	}
}
