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
ConstructorHeader addModifierLast(&[I8] modifier) {
	return this;
}
&[I8] generateWithDefinitions0(Platform platform, &[I8] definitions) {
	return generateWithAfterName(platform, "(" + definitions + ")");
}
&[I8] generateWithDefinitions(Platform platform, List<Definition> definitions) {
	&[I8] joinedDefinitions = definitions.query().map((Definition definition) => definition.generate(platform)).collect(new Joiner(", ")).orElse("");
	return this.generateWithDefinitions0(platform, joinedDefinitions);
}