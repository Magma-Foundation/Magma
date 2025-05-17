// []
import { Tuple2 } from "../../magma/api/Tuple2";
export class Tuple2Impl<A, B> implements Tuple2<A, B> {
	leftValue: A;
	rightValue: B;
	constructor (leftValue: A, rightValue: B) {
		this.leftValue = leftValue;
		this.rightValue = rightValue;
	}
	left(): A {
		return this/*auto*/.leftValue;
	}
	right(): B {
		return this/*auto*/.rightValue;
	}
}
