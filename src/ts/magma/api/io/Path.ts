/*[
	Actual: magma.annotate, 
	Namespace: magma.annotate, 
	Collector: magma.api.collect, 
	EmptyHead: magma.api.collect, 
	Head: magma.api.collect, 
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
import { Query } from "../../../magma/api/collect/query/Query";
import { Result } from "../../../magma/api/result/Result";
import { Option } from "../../../magma/api/option/Option";
import { IOException } from "../../../java/io/IOException";
export interface Path {
	writeString(output: string): Option<IOError>;
	readString(): Result<string, IOError>;
	resolveSibling(siblingName: string): Path;
	walk(): Result<List<Path>, IOError>;
	findFileName(): string;
	endsWith(suffix: string): boolean;
	relativize(source: Path): Path;
	getParent(): Path;
	query(): Query<string>;
	resolveChildSegments(children: List<string>): Path;
	resolveChild(name: string): Path;
	exists(): boolean;
	createDirectories(): Option<IOException>;
}
