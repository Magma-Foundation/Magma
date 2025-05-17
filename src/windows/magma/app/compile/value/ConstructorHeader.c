#include "./ConstructorHeader.h"
export class ConstructorHeader implements FunctionHeader<ConstructorHeader> {
}

mut generateWithAfterName(afterName: &[I8]): &[I8] {
	return "constructor " + afterName;
}
mut hasAnnotation(annotation: &[I8]): Bool {
	return false;
}
mut removeModifier(modifier: &[I8]): ConstructorHeader {
	return this;
}
mut addModifier(modifier: &[I8]): ConstructorHeader {
	return this;
}