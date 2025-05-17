#include "./ConstructorHeader.h"
export class ConstructorHeader implements FunctionHeader<ConstructorHeader> {
}

generateWithAfterName(afterName: &[I8]): &[I8] {
	return "constructor " + afterName;
}
hasAnnotation(annotation: &[I8]): Bool {
	return false;
}
removeModifier(modifier: &[I8]): ConstructorHeader {
	return this;
}
addModifier(modifier: &[I8]): ConstructorHeader {
	return this;
}