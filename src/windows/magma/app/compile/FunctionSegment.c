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

auto temp(&[I8] inner) {
	return " {" + inner/*auto*/ + indent/*&[I8]*/ + "}";
}
&[I8] generate(Platform platform, &[I8] indent) {
	var content = this/*auto*/.maybeContent(/*auto*/).map(lambdaDefinition/*auto*/).orElse(";");
	return indent/*&[I8]*/ + this/*auto*/.header.generateWithDefinitions(platform/*Platform*/, this/*auto*/.definitions(/*auto*/)) + content/*&[I8]*/;
}