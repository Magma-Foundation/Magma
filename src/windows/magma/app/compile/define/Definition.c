#include "./Definition.h"
export class Definition {
	List<&[I8]> annotations;
	List<&[I8]> modifiers;
	List<&[I8]> typeParams;
	Type type;
	&[I8] name;
	constructor (List<&[I8]> annotations, List<&[I8]> modifiers, List<&[I8]> typeParams, Type type, &[I8] name) {
		this.annotations = annotations;
		this.modifiers = modifiers;
		this.typeParams = typeParams;
		this.type = type;
		this.name = name;
	}
}

constructor (Type type, &[I8] name) {
	this/*auto*/(Lists/*auto*/.empty(/*auto*/), Lists/*auto*/.empty(/*auto*/), Lists/*auto*/.empty(/*auto*/), type/*Type*/, name/*&[I8]*/);
}
&[I8] generate(Platform platform) {
	return this/*auto*/.generateWithAfterName(platform/*Platform*/, "");
}
Option<Definition> asDefinition() {
	return new Some<Definition>(this/*auto*/);
}
&[I8] generateWithAfterName(Platform platform, &[I8] afterName) {
	var joinedTypeParams = this/*auto*/.joinTypeParams(/*auto*/);
	var joinedModifiers = this/*auto*/.joinModifiers(/*auto*/);
	if (Platform/*auto*/.Windows === platform/*Platform*/) {
		return joinedModifiers/*auto*/ + this/*auto*/.type.generateBeforeName(/*auto*/) + this/*auto*/.type.generate(/*auto*/) + " " + this/*auto*/.name + afterName/*&[I8]*/;
	}
	return joinedModifiers/*auto*/ + this/*auto*/.type.generateBeforeName(/*auto*/) + this/*auto*/.name + joinedTypeParams/*auto*/ + afterName/*&[I8]*/ + this/*auto*/.generateType(/*auto*/);
}
auto temp(&[I8] value) {
	return value/*auto*/ + " ";
}
&[I8] joinModifiers() {
	return this/*auto*/.modifiers.query(/*auto*/).map(lambdaDefinition/*auto*/).collect(new Joiner("")).orElse("");
}
&[I8] generateType() {
	if (this/*auto*/.type.isVar(/*auto*/)) {
		return "";
	}
	return ": " + this/*auto*/.type.generate(/*auto*/);
}
&[I8] joinTypeParams() {
	return Joiner/*auto*/.joinOrEmpty(this/*auto*/.typeParams, ", ", "<", ">");
}
Bool hasAnnotation(&[I8] annotation) {
	return this/*auto*/.annotations.contains(annotation/*&[I8]*/, Strings/*auto*/.equalsTo);
}
Definition removeModifier(&[I8] modifier) {
	return new Definition(this/*auto*/.annotations, this/*auto*/.modifiers.removeValue(modifier/*&[I8]*/, Strings/*auto*/.equalsTo), this/*auto*/.typeParams, this/*auto*/.type, this/*auto*/.name);
}
Definition addModifierLast(&[I8] modifier) {
	return new Definition(this/*auto*/.annotations, this/*auto*/.modifiers.addLast(modifier/*&[I8]*/), this/*auto*/.typeParams, this/*auto*/.type, this/*auto*/.name);
}
auto temp(Definition definition) {
	return definition/*auto*/.generate(platform/*Platform*/);
}
&[I8] generateWithDefinitions(Platform platform, List<Definition> definitions) {
	var joinedDefinitions = definitions/*List<Definition>*/.query(/*auto*/).map(lambdaDefinition/*auto*/).collect(new Joiner(", ")).orElse("");
	return this/*auto*/.generateWithAfterName(platform/*Platform*/, "(" + joinedDefinitions/*auto*/ + ")");
}