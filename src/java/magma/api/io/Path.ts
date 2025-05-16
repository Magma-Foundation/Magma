import { List } from "../../../magma/api/collect/List";
import { Query } from "../../../magma/api/collect/Query";
import { Result } from "../../../magma/api/result/Result";
import { Option } from "../../../magma/api/option/Option";
export interface Path  {
	writeString(output: string): Option<IOError>;
	readString(): Result<string, IOError>;
	resolveSibling(siblingName: string): Path;
	walk(): Result<List<Path>, IOError>;
	findFileName(): string;
	endsWith(suffix: string): boolean;
	relativize(source: Path): Path;
	getParent(): Path;
	query(): Query<string>;
}
