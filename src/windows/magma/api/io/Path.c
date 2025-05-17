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
