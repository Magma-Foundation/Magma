#include "./SymbolNode.h"
export class SymbolNode {
	&[I8] value;
	Type type;
	constructor (&[I8] value, Type type) {
		this.value = value;
		this.type = type;
	}
}

&[I8] generate(Platform platform) {
	return this/*auto*/.value + Main/*auto*/.generatePlaceholder(type/*Type*/.generate(/*auto*/));
}
Option<Value> toValue() {
	return new Some<Value>(this/*auto*/);
}
Option<Value> findChild() {
	return new None<Value>(/*auto*/);
}
&[I8] generate() {
	return this/*auto*/.value;
}
Bool isFunctional() {
	return false/*auto*/;
}
Bool isVar() {
	return false/*auto*/;
}
&[I8] generateBeforeName() {
	return "";
}
Option<&[I8]> generateAsEnumValue(&[I8] structureName, Platform platform) {
	return new None<&[I8]>(/*auto*/);
}