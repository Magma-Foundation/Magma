/*[
	JVMList: ..........., 
	JVMList: ..........., 
	Lists: ..........., 
	Lists: ..........., 
	Files: ........, 
	Files: ........, 
	JVMPath: ........, 
	JVMPath: ........, 
	Characters: ........, 
	Characters: ........, 
	Strings: ........, 
	Strings: ........, 
	Actual: ....., 
	Actual: ....., 
	Namespace: ....., 
	Namespace: ....., 
	Collector: ........, 
	Collector: ........, 
	EmptyHead: ..........., 
	EmptyHead: ..........., 
	FlatMapHead: ..........., 
	FlatMapHead: ..........., 
	Head: ..........., 
	Head: ..........., 
	HeadedQuery: ..........., 
	HeadedQuery: ..........., 
	MapHead: ..........., 
	MapHead: ..........., 
	RangeHead: ..........., 
	RangeHead: ..........., 
	SingleHead: ..........., 
	SingleHead: ..........., 
	ZipHead: ..........., 
	ZipHead: ..........., 
	List: ..........., 
	List: ..........., 
	ListCollector: ..........., 
	ListCollector: ..........., 
	Queries: ........, 
	Queries: ........, 
	Query: ........, 
	Query: ........, 
	Console: ........, 
	Console: ........, 
	IOError: ........, 
	IOError: ........, 
	Path: ........, 
	Path: ........, 
	None: ........, 
	None: ........, 
	Option: ........, 
	Option: ........, 
	Some: ........, 
	Some: ........, 
	Err: ........, 
	Err: ........, 
	Ok: ........, 
	Ok: ........, 
	Result: ........, 
	Result: ........, 
	Tuple2: ....., 
	Tuple2: ....., 
	Tuple2Impl: ....., 
	Tuple2Impl: ....., 
	Main: ....., 
	Main: .....
]*/
import { Tuple2 } from "../../../../Tuple2";
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
