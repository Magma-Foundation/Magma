#include "./Joiner.h"
export class Joiner implements Collector<&[I8], Option<&[I8]>> {
	&[I8] delimiter;
	constructor (&[I8] delimiter) {
		this.delimiter = delimiter;
	}
}

static Joiner empty() {
	return new Joiner("");
}
auto temp(&[I8] inner) {
	return prefix/*&[I8]*/ + inner/*auto*/ + suffix/*&[I8]*/;
}
static &[I8] joinOrEmpty(List<&[I8]> items, &[I8] delimiter, &[I8] prefix, &[I8] suffix) {
	return items/*List<&[I8]>*/.query(/*auto*/).collect(new Joiner(delimiter/*&[I8]*/)).map(lambdaDefinition/*auto*/).orElse("");
}
Option<&[I8]> createInitial() {
	return new None<&[I8]>(/*auto*/);
}
auto temp(&[I8] inner) {
	return inner/*auto*/ + this/*auto*/.delimiter + element/*&[I8]*/;
}
Option<&[I8]> fold(Option<&[I8]> maybe, &[I8] element) {
	return new Some<&[I8]>(maybe/*Option<&[I8]>*/.map(lambdaDefinition/*auto*/).orElse(element/*&[I8]*/));
}