#include "./OperationNode.h"
export class OperationNode implements Value {
	Value left;
	&[I8] targetInfix;
	Value right;
	constructor (Value left, &[I8] targetInfix, Value right) {
		this.left = left;
		this.targetInfix = targetInfix;
		this.right = right;
	}
}

&[I8] generate(Platform platform) {
	return this/*auto*/.left.generate(platform/*Platform*/) + " " + this/*auto*/.targetInfix + " " + this/*auto*/.right.generate(platform/*Platform*/);
}
Option<Value> toValue() {
	return new Some<Value>(this/*auto*/);
}
Option<Value> findChild() {
	return new None<Value>(/*auto*/);
}
Option<&[I8]> generateAsEnumValue(&[I8] structureName, Platform platform) {
	return new None<&[I8]>(/*auto*/);
}
Type type() {
	return PrimitiveType/*auto*/.Auto;
}