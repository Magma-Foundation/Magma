#include "./FunctionSegment.h"
export class FunctionSegment<S extends FunctionHeader<S>> {
	FunctionHeader<S> header;
	List<Definition> definitions;
	Option<&[I8]> maybeContent;
	constructor (FunctionHeader<S> header, List<Definition> definitions, Option<&[I8]> maybeContent) {
		this.header = header;
		this.definitions = definitions;
		this.maybeContent = maybeContent;
	}
}

&[I8] generate(Platform platform, &[I8] indent) {
	&[I8] content = this.maybeContent().map((&[I8] inner) => " {" + inner + indent + "}").orElse(";");
	return indent + this.header.generateWithDefinitions(platform, this.definitions()) + content;
}