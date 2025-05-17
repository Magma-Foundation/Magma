// [Lists, Lists, Lists, Console, Console, Console, Files, Files, Files, Characters, Characters, Characters, Strings, Strings, Strings, Actual, Actual, Actual, Namespace, Namespace, Namespace, Collector, Collector, Collector, EmptyHead, EmptyHead, EmptyHead, FlatMapHead, FlatMapHead, FlatMapHead, Head, Head, Head, HeadedQuery, HeadedQuery, HeadedQuery, MapHead, MapHead, MapHead, RangeHead, RangeHead, RangeHead, SingleHead]
import { Head } from "../../../../magma/api/collect/head/Head";
import { Option } from "../../../../magma/api/option/Option";
import { None } from "../../../../magma/api/option/None";
import { Some } from "../../../../magma/api/option/Some";
export class SingleHead<T> implements Head<T> {
	element: T;
	retrieved: boolean;
	constructor (element: T) {
		this/*auto*/.element = element/*T*/;
		this/*auto*/.retrieved = false/*auto*/;
	}
	next(): Option<T> {
		if (this/*auto*/.retrieved) {
			return new None<T>(/*auto*/);
		}
		this/*auto*/.retrieved = true/*auto*/;
		return new Some<T>(this/*auto*/.element);
	}
}
