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
import { List } from "../../../magma/api/collect/List";
import { Collector } from "../../../magma/api/collect/Collector";
import { Lists } from "../../../magma/api/collect/Lists";
export class ListCollector<T> implements Collector<T, List<T>> {
	createInitial(): List<T> {
		return Lists.empty();
	}
	fold(current: List<T>, element: T): List<T> {
		return current.addLast(element);
	}
}
