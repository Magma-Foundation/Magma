#include "./Placeholder.h"
export class Placeholder {
	&[I8] input;
	constructor (&[I8] input) {
		this.input = input;
	}
}

&[I8] generate() {
	return Main.generatePlaceholder(this.input);
}
Bool isFunctional() {
	return false;
}
&[I8] generate(Platform platform) {
	return this.generate();
}
Option<Value> findChild() {
	return new None<Value>();
}
Option<Definition> asDefinition() {
	return new None<Definition>();
}
Option<Value> toValue() {
	return new None<Value>();
}
Type resolve(CompileState state) {
	return PrimitiveType.Unknown;
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