import { Option } from "../../../magma/api/option/Option";
import { Collector } from "../../../magma/api/collect/Collector";
import { List } from "../../../magma/api/collect/list/List";
import { None } from "../../../magma/api/option/None";
import { Some } from "../../../magma/api/option/Some";
export class Joiner implements Collector<string, Option<string>> {
	delimiter: string;
	constructor (delimiter: string) {
		this.delimiter = delimiter;
	}
	static empty(): Joiner {
		return new Joiner("");
	}
	static joinOrEmpty(items: List<string>, delimiter: string, prefix: string, suffix: string): string {
		return items.query().collect(new Joiner(delimiter)).map((inner: string) => prefix + inner + suffix).orElse("");
	}
	createInitial(): Option<string> {
		return new None<string>();
	}
	fold(maybe: Option<string>, element: string): Option<string> {
		return new Some<string>(maybe.map((inner: string) => inner + this.delimiter + element).orElse(element));
	}
}
