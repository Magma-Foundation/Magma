#include "./ConstructorHeader.h"
export class ConstructorHeader implements FunctionHeader<ConstructorHeader> {
}

&[I8] generateWithAfterName(Platform platform, &[I8] afterName) {
	return "constructor " + afterName;
}
Bool hasAnnotation(&[I8] annotation) {
	return false;
}
ConstructorHeader removeModifier(&[I8] modifier) {
	return this;
}
ConstructorHeader addModifier(&[I8] modifier) {
	return this;
}