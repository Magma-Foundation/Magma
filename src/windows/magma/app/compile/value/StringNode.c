#include "./StringNode.h"
export class StringNode implements Value {
	&[I8] value;
	constructor (&[I8] value) {
		this.value = value;
	}
}

&[I8] generate(Platform platform) {
	return "\"" + this/*auto*/.value + "\"";
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