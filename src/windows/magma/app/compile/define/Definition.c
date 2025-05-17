#include "./Definition.h"
export class Definition {
	annotations: List<&[I8]>;
	modifiers: List<&[I8]>;
	typeParams: List<&[I8]>;
	type: Type;
	name: &[I8];
	constructor (annotations: List<&[I8]>, modifiers: List<&[I8]>, typeParams: List<&[I8]>, type: Type, name: &[I8]) {
		this.annotations = annotations;
		this.modifiers = modifiers;
		this.typeParams = typeParams;
		this.type = type;
		this.name = name;
	}
}

generate(): &[I8] {
	return this.generateWithAfterName("");
}
asDefinition(): Option<Definition> {
	return new Some<Definition>(this);
}
generateWithAfterName(afterName: &[I8]): &[I8] {
	let joinedTypeParams: &[I8] = this.joinTypeParams();
	let joinedModifiers: &[I8] = this.modifiers.query().map((value: &[I8]) => value + " ").collect(new Joiner("")).orElse("");
	return joinedModifiers + this.type.generateBeforeName() + this.name + joinedTypeParams + afterName + this.generateType();
}
generateType(): &[I8] {
	if (this.type.isVar()) {
		return "";
	}
	return ": " + this.type.generate();
}
joinTypeParams(): &[I8] {
	return Joiner.joinOrEmpty(this.typeParams, ", ", "<", ">");
}
hasAnnotation(annotation: &[I8]): Bool {
	return this.annotations.contains(annotation, Strings.equalsTo);
}
removeModifier(modifier: &[I8]): Definition {
	return new Definition(this.annotations, this.modifiers.removeValue(modifier, Strings.equalsTo), this.typeParams, this.type, this.name);
}
addModifier(modifier: &[I8]): Definition {
	return new Definition(this.annotations, this.modifiers.addFirst(modifier), this.typeParams, this.type, this.name);
}