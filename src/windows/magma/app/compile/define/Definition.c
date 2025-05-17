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

&[I8] generate(Platform platform) {
	return this.generateWithAfterName(platform, "");
}
Option<Definition> asDefinition() {
	return new Some<Definition>(this);
}
&[I8] generateWithAfterName(Platform platform, &[I8] afterName) {
	&[I8] joinedTypeParams = this.joinTypeParams();
	&[I8] joinedModifiers = this.joinModifiers();
	if (Platform.Windows === platform) {
		return joinedModifiers + this.type.generateBeforeName() + this.type.generate() + " " + this.name + afterName;
	}
	return joinedModifiers + this.type.generateBeforeName() + this.name + joinedTypeParams + afterName + this.generateType();
}
&[I8] joinModifiers() {
	return this.modifiers.query().map((&[I8] value) => value + " ").collect(new Joiner("")).orElse("");
}
&[I8] generateType() {
	if (this.type.isVar()) {
		return "";
	}
	return ": " + this.type.generate();
}
&[I8] joinTypeParams() {
	return Joiner.joinOrEmpty(this.typeParams, ", ", "<", ">");
}
Bool hasAnnotation(&[I8] annotation) {
	return this.annotations.contains(annotation, Strings.equalsTo);
}
Definition removeModifier(&[I8] modifier) {
	return new Definition(this.annotations, this.modifiers.removeValue(modifier, Strings.equalsTo), this.typeParams, this.type, this.name);
}
Definition addModifierLast(&[I8] modifier) {
	return new Definition(this.annotations, this.modifiers.addLast(modifier), this.typeParams, this.type, this.name);
}
&[I8] generateWithDefinitions(Platform platform, List<Definition> definitions) {
	&[I8] joinedDefinitions = definitions.query().map((Definition definition) => definition.generate(platform)).collect(new Joiner(", ")).orElse("");
	return this.generateWithAfterName(platform, "(" + joinedDefinitions + ")");
}