#include "./AccessNode.h"
export class AccessNode implements Value {
	Value child;
	&[I8] property;
	constructor (Value child, &[I8] property) {
		this.child = child;
		this.property = property;
	}
}

&[I8] generate(Platform platform) {
	return this.child.generate(platform) + "." + this.property;
}
Option<Value> toValue() {
	return new Some<Value>(this);
}
Option<Value> findChild() {
	return new Some<Value>(this.child);
}
Type resolve(CompileState state) {
	return PrimitiveType.Unknown;
}
Option<&[I8]> generateAsEnumValue(&[I8] structureName, Platform platform) {
	return new None<&[I8]>();
}