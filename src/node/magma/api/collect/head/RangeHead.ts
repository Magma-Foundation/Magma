// [Lists, Lists, Lists, Console, Console, Console, Files, Files, Files, Characters, Characters, Characters, Strings, Strings, Strings, Actual, Actual, Actual, Namespace, Namespace, Namespace, Collector, Collector, Collector, EmptyHead, EmptyHead, EmptyHead, FlatMapHead, FlatMapHead, FlatMapHead, Head, Head, Head, HeadedQuery, HeadedQuery, HeadedQuery, MapHead, MapHead, MapHead, RangeHead]
import { Head } from "../../../../magma/api/collect/head/Head";
import { Option } from "../../../../magma/api/option/Option";
import { None } from "../../../../magma/api/option/None";
import { Some } from "../../../../magma/api/option/Some";
export class RangeHead implements Head<number> {
	length: number;
	counter: number;
	constructor (length: number) {
		this/*auto*/.length = length/*number*/;
		this/*auto*/.counter = 0/*auto*/;
	}
	next(): Option<number> {
		if (this/*auto*/.counter >= this/*auto*/.length) {
			return new None<number>(/*auto*/);
		}
		let value = this/*auto*/.counter;
		this/*auto*/.counter++;
		return new Some<number>(value/*auto*/);
	}
}
