import { Tuple2 } from "magma/api/Tuple2";
export class Tuple2Impl<A, B> implements Tuple2<A, B> {
	mut leftValue: A;
	mut rightValue: B;
	constructor (mut leftValue: A, mut rightValue: B) {
		this.leftValue = leftValue;
		this.rightValue = rightValue;
	}
	mut left(): A {
		return this.leftValue;
	}
	mut right(): B {
		return this.rightValue;
	}
}
