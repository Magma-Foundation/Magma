// [Lists, Lists, Lists, Console, Console, Console, Files, Files, Files, Characters, Characters, Characters, Strings, Strings, Strings, Actual, Actual, Actual, Namespace, Namespace, Namespace, Collector, Collector, Collector, EmptyHead, EmptyHead, EmptyHead, FlatMapHead, FlatMapHead, FlatMapHead, Head, Head, Head, HeadedQuery, HeadedQuery, HeadedQuery, MapHead, MapHead, MapHead, RangeHead, RangeHead, RangeHead, SingleHead, SingleHead, SingleHead, ZipHead, ZipHead, ZipHead, Joiner]
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
		return items/*List<string>*/.query(/*auto*/).collect(new Joiner(delimiter/*string*/)).map((inner: string) => prefix/*string*/ + inner/*auto*/ + suffix/*string*/).orElse("");
	}
	createInitial(): Option<string> {
		return new None<string>(/*auto*/);
	}
	fold(maybe: Option<string>, element: string): Option<string> {
		return new Some<string>(maybe/*Option<string>*/.map((inner: string) => inner/*auto*/ + this/*auto*/.delimiter + element/*string*/).orElse(element/*string*/));
	}
}
