#include "./ConstructorHeader.h"
export class ConstructorHeader implements FunctionHeader<ConstructorHeader> {
}

&[I8] generateWithAfterName(Platform platform, &[I8] afterName) {
	return "constructor " + afterName/*&[I8]*/;
}
Bool hasAnnotation(&[I8] annotation) {
	return false/*auto*/;
}
ConstructorHeader removeModifier(&[I8] modifier) {
	return this/*auto*/;
}
ConstructorHeader addModifierLast(&[I8] modifier) {
	return this/*auto*/;
}
&[I8] generateWithDefinitions0(Platform platform, &[I8] definitions) {
	return generateWithAfterName/*auto*/(platform/*Platform*/, "(" + definitions/*&[I8]*/ + ")");
}
auto temp(Definition definition) {
	return definition/*auto*/.generate(platform/*Platform*/);
}
&[I8] generateWithDefinitions(Platform platform, List<Definition> definitions) {
	&[I8] joinedDefinitions = definitions/*List<Definition>*/.query(/*auto*/).map(lambdaDefinition/*auto*/).collect(new Joiner(", ")).orElse("");
	return this/*auto*/.generateWithDefinitions0(platform/*Platform*/, joinedDefinitions/*auto*/);
}