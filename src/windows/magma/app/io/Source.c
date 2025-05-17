#include "./Source.h"
export class Source {
	mut sourceDirectory: Path;
	mut source: Path;
	constructor (mut sourceDirectory: Path, mut source: Path) {
		this.sourceDirectory = sourceDirectory;
		this.source = source;
	}
}

mut read(): Result<&[I8], IOError> {
	return this.source.readString();
}
mut computeName(): &[I8] {
	let fileName = this.source.findFileName();
	let separator = fileName.lastIndexOf(".");
	return fileName.substring(0, separator);
}
mut computeNamespace(): List<&[I8]> {
	return this.sourceDirectory.relativize(this.source).getParent().query().collect(new ListCollector<&[I8]>());
}
mut computeLocation(): Location {
	return new Location(this.computeNamespace(), this.computeName());
}