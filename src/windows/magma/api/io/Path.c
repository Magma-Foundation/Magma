#include "./Path.h"
export interface Path {
	&[I8] asString();
	Option<IOError> writeString(&[I8] output);
	Result<&[I8], IOError> readString();
	Result<List<Path>, IOError> walk();
	&[I8] findFileName();
	Bool endsWith(&[I8] suffix);
	Path relativize(Path source);
	Path getParent();
	Query<&[I8]> query();
	Path resolveChildSegments(List<&[I8]> children);
	Path resolveChild(&[I8] name);
	Bool exists();
	Option<IOError> createDirectories();
}
