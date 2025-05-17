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
static &[I8] joinOrEmpty(List<&[I8]> items, &[I8] delimiter, &[I8] prefix, &[I8] suffix) {
	return items.query().collect(new Joiner(delimiter)).map((&[I8] inner) => prefix + inner + suffix).orElse("");
}
Option<&[I8]> createInitial() {
	return new None<&[I8]>();
}
Option<&[I8]> fold(Option<&[I8]> maybe, &[I8] element) {
	return new Some<&[I8]>(maybe.map((&[I8] inner) => inner + this.delimiter + element).orElse(element));
}