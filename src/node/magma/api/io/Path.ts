/*[
	Actual, 
	Characters, 
	Collector, 
	Console, 
	EmptyHead, 
	Files, 
	FlatMapHead, 
	Head, 
	HeadedQuery, 
	IOError, 
	Joiner, 
	List, 
	ListCollector, 
	Lists, 
	MapHead, 
	Namespace, 
	Path, 
	Queries, 
	Query, 
	RangeHead, 
	SingleHead, 
	Strings, 
	ZipHead
]*/
import { IOError } from "../../../magma/api/io/IOError";
import { Option } from "../../../magma/api/option/Option";
import { Result } from "../../../magma/api/result/Result";
import { List } from "../../../magma/api/collect/list/List";
import { Query } from "../../../magma/api/collect/Query";
export interface Path {
	asString(): string;
	writeString(output: string): Option<IOError>;
	readString(): Result<string, IOError>;
	walk(): Result<List<Path>, IOError>;
	findFileName(): string;
	endsWith(suffix: string): boolean;
	relativize(source: Path): Path;
	getParent(): Path;
	query(): Query<string>;
	resolveChildSegments(children: List<string>): Path;
	resolveChild(name: string): Path;
	exists(): boolean;
	createDirectories(): Option<IOError>;
}
