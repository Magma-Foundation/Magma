#include "./OperationNode.h"
export class OperationNode implements Value {
	left: Value;
	targetInfix: &[I8];
	right: Value;
	constructor (left: Value, targetInfix: &[I8], right: Value) {
		this.left = left;
		this.targetInfix = targetInfix;
		this.right = right;
	}
}

generate(): &[I8] {
	return this.left.generate() + " " + this.targetInfix + " " + this.right.generate();
}
toValue(): Option<Value> {
	return new Some<Value>(this);
}
findChild(): Option<Value> {
	return new None<Value>();
}
resolve(state: CompileState): Type {
	return PrimitiveType.Unknown;
}
generateAsEnumValue(structureName: &[I8]): Option<&[I8]> {
	return new None<&[I8]>();
}