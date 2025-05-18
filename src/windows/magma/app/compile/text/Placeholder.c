#include "./Placeholder.h"
export class Placeholder {
	&[I8] input;
	constructor (&[I8] input) {
		this.input = input;
	}
}

&[I8] generate() {
	return Main/*auto*/.generatePlaceholder(this/*auto*/.input);
}
Bool isFunctional() {
	return false/*auto*/;
}
&[I8] generate(Platform platform) {
	return this/*auto*/.generate(/*auto*/);
}
Option<Value> findChild() {
	return new None<Value>(/*auto*/);
}
Option<Definition> asDefinition() {
	return new None<Definition>(/*auto*/);
}
Option<Value> toValue() {
	return new None<Value>(/*auto*/);
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
Type type() {
	return PrimitiveType/*auto*/.Auto;
}