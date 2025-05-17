#include "./AccessNode.h"
export class AccessNode implements Value {
	mut child: Value;
	mut property: &[I8];
	constructor (mut child: Value, mut property: &[I8]) {
		this.child = child;
		this.property = property;
	}
}

mut generate(): &[I8] {
	return this.child.generate() + "." + this.property;
}
mut toValue(): Option<Value> {
	return new Some<Value>(this);
}
mut findChild(): Option<Value> {
	return new Some<Value>(this.child);
}
mut resolve(state: CompileState): Type {
	return PrimitiveType.Unknown;
}
mut generateAsEnumValue(structureName: &[I8]): Option<&[I8]> {
	return new None<&[I8]>();
}