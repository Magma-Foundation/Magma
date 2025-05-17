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
	return this.left.generate(platform) + " " + this.targetInfix + " " + this.right.generate(platform);
}
Option<Value> toValue() {
	return new Some<Value>(this);
}
Option<Value> findChild() {
	return new None<Value>();
}
Type resolve(CompileState state) {
	return PrimitiveType.Unknown;
}
Option<&[I8]> generateAsEnumValue(&[I8] structureName, Platform platform) {
	return new None<&[I8]>();
}