#include "./Definition.h"
export class Definition {
	mut annotations: List<&[I8]>;
	mut modifiers: List<&[I8]>;
	mut typeParams: List<&[I8]>;
	mut type: Type;
	mut name: &[I8];
	constructor (mut annotations: List<&[I8]>, mut modifiers: List<&[I8]>, mut typeParams: List<&[I8]>, mut type: Type, mut name: &[I8]) {
		this.annotations = annotations;
		this.modifiers = modifiers;
		this.typeParams = typeParams;
		this.type = type;
		this.name = name;
	}
	mut generate(): &[I8] {
		return this.generateWithAfterName("");
	}
	mut asDefinition(): Option<Definition> {
		return new Some<Definition>(this);
	}
	mut generateWithAfterName(afterName: &[I8]): &[I8] {
		let joinedTypeParams = this.joinTypeParams();
		let joinedModifiers = this.modifiers.query().map((mut value: &[I8]) => value + " ").collect(new Joiner("")).orElse("");
		return joinedModifiers + this.type.generateBeforeName() + this.name + joinedTypeParams + afterName + this.generateType();
	}
	mut generateType(): &[I8] {
		if (this.type.isVar()) {
			return "";
		}
		return ": " + this.type.generate();
	}
	mut joinTypeParams(): &[I8] {
		return Joiner.joinOrEmpty(this.typeParams, ", ", "<", ">");
	}
	mut hasAnnotation(annotation: &[I8]): Bool {
		return this.annotations.contains(annotation, Strings.equalsTo);
	}
	mut removeModifier(modifier: &[I8]): Definition {
		return new Definition(this.annotations, this.modifiers.removeValue(modifier, Strings.equalsTo), this.typeParams, this.type, this.name);
	}
	mut addModifier(modifier: &[I8]): Definition {
		return new Definition(this.annotations, this.modifiers.addFirst(modifier), this.typeParams, this.type, this.name);
	}
}
