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
import { IOError } from "magma/api/io/IOError";
import { Option } from "magma/api/option/Option";
import { Result } from "magma/api/result/Result";
import { List } from "magma/api/collect/list/List";
import { Query } from "magma/api/collect/Query";
export interface Path {
	mut asString(): &[I8];
	mut writeString(mut output: &[I8]): Option<IOError>;
	mut readString(): Result<&[I8], IOError>;
	mut walk(): Result<List<Path>, IOError>;
	mut findFileName(): &[I8];
	mut endsWith(mut suffix: &[I8]): Bool;
	mut relativize(mut source: Path): Path;
	mut getParent(): Path;
	mut query(): Query<&[I8]>;
	mut resolveChildSegments(mut children: List<&[I8]>): Path;
	mut resolveChild(mut name: &[I8]): Path;
	mut exists(): Bool;
	mut createDirectories(): Option<IOError>;
}
