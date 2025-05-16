/*[
	Actual: magma.annotate, 
	Namespace: magma.annotate, 
	Collector: magma.api.collect, 
	EmptyHead: magma.api.collect, 
	Head: magma.api.collect, 
	List: magma.api.collect, 
	ListCollector: magma.api.collect, 
	Lists: magma.api.collect, 
	HeadedQuery: magma.api.collect.query, 
	Query: magma.api.collect.query
]*/
import { None } from "../../../magma/api/option/None";
import { Option } from "../../../magma/api/option/Option";
import { Some } from "../../../magma/api/option/Some";
import { Head } from "./Head";
export class RangeHead implements Head<number> {
	length: number;
	counter: number;
	constructor (length: number) {
		this.length = length;
		this.counter = 0;
	}
	next(): Option<number> {
		if (this.counter >= this.length){
			return new None<number>();
		}
		let value = this.counter;
		this.counter++;
		return new Some<number>(value);
	}
}
