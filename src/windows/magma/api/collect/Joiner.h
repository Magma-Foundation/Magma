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
	Type: magma.api, 
	Definition: magma.app, 
	Main: magma.app, 
	MethodHeader: magma.app, 
	Parameter: magma.app
]*/
import { Option } from "magma/api/option/Option";
import { Collector } from "magma/api/collect/Collector";
import { List } from "magma/api/collect/list/List";
import { None } from "magma/api/option/None";
import { Some } from "magma/api/option/Some";
export class Joiner implements Collector<&[I8], Option<&[I8]>> {
	mut delimiter: &[I8];
	constructor (mut delimiter: &[I8]) {
		this.delimiter = delimiter;
	}
	mut static empty(): Joiner {
		return new Joiner("");
	}
	mut static joinOrEmpty(items: List<&[I8]>, mut delimiter: &[I8], mut prefix: &[I8], mut suffix: &[I8]): &[I8] {
		return items.query().collect(new Joiner(delimiter)).map((mut inner: &[I8]) => prefix + inner + suffix).orElse("");
	}
	mut createInitial(): Option<&[I8]> {
		return new None<&[I8]>();
	}
	mut fold(maybe: Option<&[I8]>, element: &[I8]): Option<&[I8]> {
		return new Some<&[I8]>(maybe.map((mut inner: &[I8]) => inner + this.delimiter + element).orElse(element));
	}
}
