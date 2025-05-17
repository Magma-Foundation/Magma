#include "./Source.h"
export class Source {
	sourceDirectory: Path;
	source: Path;
	constructor (sourceDirectory: Path, source: Path) {
		this.sourceDirectory = sourceDirectory;
		this.source = source;
	}
}

read(): Result<&[I8], IOError> {
	return this.source.readString();
}
computeName(): &[I8] {
	let fileName: &[I8] = this.source.findFileName();
	let separator: number = fileName.lastIndexOf(".");
	return fileName.substring(0, separator);
}
computeNamespace(): List<&[I8]> {
	return this.sourceDirectory.relativize(this.source).getParent().query().collect(new ListCollector<&[I8]>());
}
computeLocation(): Location {
	return new Location(this.computeNamespace(), this.computeName());
}