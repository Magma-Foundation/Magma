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
	Strings
]*/
import { Head } from "../../../../magma/api/collect/head/Head";
import { Option } from "../../../../magma/api/option/Option";
export class MapHead<T, R> implements Head<R> {
	head: Head<T>;
	mapper: (arg0 : T) => R;
	constructor (head: Head<T>, mapper: (arg0 : T) => R) {
		this.head = head;
		this.mapper = mapper;
	}
	next(): Option<R> {
		return this/*auto*/.head.next(/*auto*/).map(this/*auto*/.mapper);
	}
}
