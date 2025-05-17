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
	return this/*auto*/.child.generate(platform/*auto*/) + "." + this/*auto*/.property;
}
Option<Value> toValue() {
	return new Some<Value>(this/*auto*/);
}
Option<Value> findChild() {
	return new Some<Value>(this/*auto*/.child);
}
Type resolve(CompileState state) {
	return PrimitiveType/*auto*/.Auto;
}
Option<&[I8]> generateAsEnumValue(&[I8] structureName, Platform platform) {
	return new None<&[I8]>(/*auto*/);
}