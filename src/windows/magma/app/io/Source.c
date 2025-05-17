#include "./Source.h"
export class Source {
	Path sourceDirectory;
	Path source;
	constructor (Path sourceDirectory, Path source) {
		this.sourceDirectory = sourceDirectory;
		this.source = source;
	}
}

Result<&[I8], IOError> read() {
	return this.source.readString();
}
&[I8] computeName() {
	&[I8] fileName = this.source.findFileName();
	number separator = fileName.lastIndexOf(".");
	return fileName.substring(0, separator);
}
List<&[I8]> computeNamespace() {
	return this.sourceDirectory.relativize(this.source).getParent().query().collect(new ListCollector<&[I8]>());
}
Location computeLocation() {
	return new Location(this.computeNamespace(), this.computeName());
}