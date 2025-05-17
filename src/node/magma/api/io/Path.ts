// [Lists, Lists, Lists, Console, Console, Console, Files, Files, Files, Characters, Characters, Characters, Strings, Strings, Strings, Actual, Actual, Actual, Namespace, Namespace, Namespace, Collector, Collector, Collector, EmptyHead, EmptyHead, EmptyHead, FlatMapHead, FlatMapHead, FlatMapHead, Head, Head, Head, HeadedQuery, HeadedQuery, HeadedQuery, MapHead, MapHead, MapHead, RangeHead, RangeHead, RangeHead, SingleHead, SingleHead, SingleHead, ZipHead, ZipHead, ZipHead, Joiner, Joiner, Joiner, List, List, List, ListCollector, ListCollector, ListCollector, Queries, Queries, Queries, Query, Query, Query, IOError, IOError, IOError, Path]
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
