#include "./ConstructorHeader.h"
export class ConstructorHeader implements FunctionHeader<ConstructorHeader> {
}

&[I8] generateWithAfterName(Platform platform, &[I8] afterName) {
	return "constructor " + afterName/*auto*/;
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
	return generateWithAfterName/*auto*/(platform/*auto*/, "(" + definitions/*auto*/ + ")");
}
auto temp(Definition definition) {
	return definition/*auto*/.generate(platform/*auto*/);
}
&[I8] generateWithDefinitions(Platform platform, List<Definition> definitions) {
	&[I8] joinedDefinitions = definitions/*auto*/.query(/*auto*/).map(lambdaDefinition/*auto*/).collect(new Joiner(", ")).orElse("");
	return this/*auto*/.generateWithDefinitions0(platform/*auto*/, joinedDefinitions/*auto*/);
}