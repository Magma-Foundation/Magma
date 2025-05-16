import { List } from "../../../magma/api/collect/List";
import { Query } from "../../../magma/api/collect/query/Query";
import { Result } from "../../../magma/api/result/Result";
import { Option } from "../../../magma/api/option/Option";
import { IOException } from "../../../java/io/IOException";
import { Option } from "./Option";
import { Result } from "./Result";
import { List } from "./List";
import { Query } from "./Query";
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
