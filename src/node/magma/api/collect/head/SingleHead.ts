/*[
	Actual, 
	Characters, 
	Collector, 
	Console, 
	EmptyHead, 
	Files, 
	FlatMapHead, 
	Head, 
	HeadedQuery, 
	Lists, 
	MapHead, 
	Namespace, 
	RangeHead, 
	SingleHead, 
	Strings
]*/
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
