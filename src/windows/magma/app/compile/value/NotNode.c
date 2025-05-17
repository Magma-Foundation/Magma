#include "./NotNode.h"
export class NotNode implements Value {
	&[I8] child;
	constructor (&[I8] child) {
		this.child = child;
	}
}

&[I8] generate(Platform platform) {
	return this/*auto*/.child;
}
Option<Value> toValue() {
	return new Some<Value>(this/*auto*/);
}
Option<Value> findChild() {
	return new None<Value>(/*auto*/);
}
Type resolve(CompileState state) {
	return PrimitiveType/*auto*/.Auto;
}
Option<&[I8]> generateAsEnumValue(&[I8] structureName, Platform platform) {
	return new None<&[I8]>(/*auto*/);
}