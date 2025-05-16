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
	Query: magma.api.collect.query, 
	RangeHead: magma.api.collect, 
	SingleHead: magma.api.collect, 
	Console: magma.api, 
	IOError: magma.api.io, 
	Path: magma.api.io, 
	None: magma.api.option, 
	Option: magma.api.option, 
	Some: magma.api.option, 
	Result: magma.api.result, 
	Characters: magma.api.text, 
	Strings: magma.api.text, 
	Tuple2: magma.api
]*/
import { Tuple2 } from "./Tuple2";
export class Tuple2Impl<A, B> implements Tuple2<A, B> {
	leftValue: A;
	rightValue: B;
	constructor (leftValue: A, rightValue: B) {
		this.leftValue = leftValue;
		this.rightValue = rightValue;
	}
	left(): A {
		return this.leftValue;
	}
	right(): B {
		return this.rightValue;
	}
}
