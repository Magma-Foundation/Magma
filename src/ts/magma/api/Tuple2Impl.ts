// [Actual, Namespace, Collector, EmptyHead, Head, List, ListCollector, Lists, HeadedQuery, Query, RangeHead, SingleHead, Console, IOError, Path, None, Option, Some, Result, Characters, Strings, Tuple2]
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
