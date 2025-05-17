#include "./Joiner.h"
export class Joiner implements Collector<&[I8], Option<&[I8]>> {
	mut delimiter: &[I8];
	constructor (mut delimiter: &[I8]) {
		this.delimiter = delimiter;
	}
	mut static empty(): Joiner {
		return new Joiner("");
	}
	mut static joinOrEmpty(items: List<&[I8]>, delimiter: &[I8], prefix: &[I8], suffix: &[I8]): &[I8] {
		return items.query().collect(new Joiner(delimiter)).map((mut inner: &[I8]) => prefix + inner + suffix).orElse("");
	}
	mut createInitial(): Option<&[I8]> {
		return new None<&[I8]>();
	}
	mut fold(maybe: Option<&[I8]>, element: &[I8]): Option<&[I8]> {
		return new Some<&[I8]>(maybe.map((mut inner: &[I8]) => inner + this.delimiter + element).orElse(element));
	}
}
