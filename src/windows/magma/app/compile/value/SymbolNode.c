#include "./SymbolNode.h"
export class SymbolNode {
	&[I8] value;
	constructor (&[I8] value) {
		this.value = value;
	}
}

&[I8] generate(Platform platform) {
	return this.value;
}
Type resolve(CompileState state) {
	return state.resolve(this.value).orElse(PrimitiveType.Auto);
}
Option<Value> toValue() {
	return new Some<Value>(this);
}
Option<Value> findChild() {
	return new None<Value>();
}
&[I8] generate() {
	return this.value;
}
Bool isFunctional() {
	return false;
}
Bool isVar() {
	return false;
}
&[I8] generateBeforeName() {
	return "";
}
Option<&[I8]> generateAsEnumValue(&[I8] structureName, Platform platform) {
	return new None<&[I8]>();
}