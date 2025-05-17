#include "./OperationNode.h"
export class OperationNode implements Value {
	mut left: Value;
	mut targetInfix: &[I8];
	mut right: Value;
	constructor (mut left: Value, mut targetInfix: &[I8], mut right: Value) {
		this.left = left;
		this.targetInfix = targetInfix;
		this.right = right;
	}
}

mut generate(): &[I8] {
	return this.left.generate() + " " + this.targetInfix + " " + this.right.generate();
}
mut toValue(): Option<Value> {
	return new Some<Value>(this);
}
mut findChild(): Option<Value> {
	return new None<Value>();
}
mut resolve(state: CompileState): Type {
	return PrimitiveType.Unknown;
}
mut generateAsEnumValue(structureName: &[I8]): Option<&[I8]> {
	return new None<&[I8]>();
}