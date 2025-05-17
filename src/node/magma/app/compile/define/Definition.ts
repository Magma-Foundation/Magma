import { List } from "../../../../magma/api/collect/list/List";
import { Type } from "../../../../magma/api/Type";
import { Platform } from "../../../../magma/app/io/Platform";
import { Option } from "../../../../magma/api/option/Option";
import { Some } from "../../../../magma/api/option/Some";
import { Joiner } from "../../../../magma/api/collect/Joiner";
import { Strings } from "../../../../jvm/api/text/Strings";
export class Definition {
	annotations: List<string>;
	modifiers: List<string>;
	typeParams: List<string>;
	type: Type;
	name: string;
	constructor (annotations: List<string>, modifiers: List<string>, typeParams: List<string>, type: Type, name: string) {
		this.annotations = annotations;
		this.modifiers = modifiers;
		this.typeParams = typeParams;
		this.type = type;
		this.name = name;
	}
	generate(platform: Platform): string {
		return this.generateWithAfterName(platform, "");
	}
	asDefinition(): Option<Definition> {
		return new Some<Definition>(this);
	}
	generateWithAfterName(platform: Platform, afterName: string): string {
		let joinedTypeParams: string = this.joinTypeParams();
		let joinedModifiers: string = this.joinModifiers();
		if (Platform.Windows === platform) {
			return joinedModifiers + this.type.generateBeforeName() + this.type.generate() + " " + this.name + afterName;
		}
		return joinedModifiers + this.type.generateBeforeName() + this.name + joinedTypeParams + afterName + this.generateType();
	}
	joinModifiers(): string {
		return this.modifiers.query().map((value: string) => value + " ").collect(new Joiner("")).orElse("");
	}
	generateType(): string {
		if (this.type.isVar()) {
			return "";
		}
		return ": " + this.type.generate();
	}
	joinTypeParams(): string {
		return Joiner.joinOrEmpty(this.typeParams, ", ", "<", ">");
	}
	hasAnnotation(annotation: string): boolean {
		return this.annotations.contains(annotation, Strings.equalsTo);
	}
	removeModifier(modifier: string): Definition {
		return new Definition(this.annotations, this.modifiers.removeValue(modifier, Strings.equalsTo), this.typeParams, this.type, this.name);
	}
	addModifierLast(modifier: string): Definition {
		return new Definition(this.annotations, this.modifiers.addLast(modifier), this.typeParams, this.type, this.name);
	}
	generateWithDefinitions(platform: Platform, definitions: List<Definition>): string {
		let joinedDefinitions: string = definitions.query().map((definition: Definition) => definition.generate(platform)).collect(new Joiner(", ")).orElse("");
		return this.generateWithAfterName(platform, "(" + joinedDefinitions + ")");
	}
}
