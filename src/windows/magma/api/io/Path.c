#include "./Path.h"
export interface Path {
	asString(): &[I8];
	writeString(output: &[I8]): Option<IOError>;
	readString(): Result<&[I8], IOError>;
	walk(): Result<List<Path>, IOError>;
	findFileName(): &[I8];
	endsWith(suffix: &[I8]): Bool;
	relativize(source: Path): Path;
	getParent(): Path;
	query(): Query<&[I8]>;
	resolveChildSegments(children: List<&[I8]>): Path;
	resolveChild(name: &[I8]): Path;
	exists(): Bool;
	createDirectories(): Option<IOError>;
}
