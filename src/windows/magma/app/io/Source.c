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
	return this/*auto*/.source.readString(/*auto*/);
}
&[I8] computeName() {
	var fileName = this/*auto*/.source.findFileName(/*auto*/);
	var separator = fileName/*auto*/.lastIndexOf(".");
	return fileName/*auto*/.substring(0/*auto*/, separator/*auto*/);
}
List<&[I8]> computeNamespace() {
	return this/*auto*/.sourceDirectory.relativize(this/*auto*/.source).getParent(/*auto*/).query(/*auto*/).collect(new ListCollector<&[I8]>(/*auto*/));
}
Location computeLocation() {
	return new Location(this/*auto*/.computeNamespace(/*auto*/), this/*auto*/.computeName(/*auto*/));
}