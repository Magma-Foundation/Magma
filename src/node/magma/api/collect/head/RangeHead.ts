// [Lists, Console, Files, Characters, Strings, Actual, Namespace, Collector, EmptyHead, FlatMapHead, Head, HeadedQuery, MapHead, RangeHead]
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
