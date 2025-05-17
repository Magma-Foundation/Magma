import { List } from "../../../../magma/api/collect/list/List";
import { Type } from "../../../../magma/api/Type";
import { Lists } from "../../../../jvm/api/collect/list/Lists";
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
	constructor (type: Type, name: string) {
		this/*auto*/(Lists/*auto*/.empty(/*auto*/), Lists/*auto*/.empty(/*auto*/), Lists/*auto*/.empty(/*auto*/), type/*auto*/, name/*auto*/);
	}
	generate(platform: Platform): string {
		return this/*auto*/.generateWithAfterName(platform/*auto*/, "");
	}
	asDefinition(): Option<Definition> {
		return new Some<Definition>(this/*auto*/);
	}
	generateWithAfterName(platform: Platform, afterName: string): string {
		let joinedTypeParams: string = this/*auto*/.joinTypeParams(/*auto*/);
		let joinedModifiers: string = this/*auto*/.joinModifiers(/*auto*/);
		if (Platform/*auto*/.Windows === platform/*auto*/) {
			return joinedModifiers/*auto*/ + this/*auto*/.type.generateBeforeName(/*auto*/) + this/*auto*/.type.generate(/*auto*/) + " " + this/*auto*/.name + afterName/*auto*/;
		}
		return joinedModifiers/*auto*/ + this/*auto*/.type.generateBeforeName(/*auto*/) + this/*auto*/.name + joinedTypeParams/*auto*/ + afterName/*auto*/ + this/*auto*/.generateType(/*auto*/);
	}
	joinModifiers(): string {
		return this/*auto*/.modifiers.query(/*auto*/).map((value: string) => value/*auto*/ + " ").collect(new Joiner("")).orElse("");
	}
	generateType(): string {
		if (this/*auto*/.type.isVar(/*auto*/)) {
			return "";
		}
		return ": " + this/*auto*/.type.generate(/*auto*/);
	}
	joinTypeParams(): string {
		return Joiner/*auto*/.joinOrEmpty(this/*auto*/.typeParams, ", ", "<", ">");
	}
	hasAnnotation(annotation: string): boolean {
		return this/*auto*/.annotations.contains(annotation/*auto*/, Strings/*auto*/.equalsTo);
	}
	removeModifier(modifier: string): Definition {
		return new Definition(this/*auto*/.annotations, this/*auto*/.modifiers.removeValue(modifier/*auto*/, Strings/*auto*/.equalsTo), this/*auto*/.typeParams, this/*auto*/.type, this/*auto*/.name);
	}
	addModifierLast(modifier: string): Definition {
		return new Definition(this/*auto*/.annotations, this/*auto*/.modifiers.addLast(modifier/*auto*/), this/*auto*/.typeParams, this/*auto*/.type, this/*auto*/.name);
	}
	generateWithDefinitions(platform: Platform, definitions: List<Definition>): string {
		let joinedDefinitions: string = definitions/*auto*/.query(/*auto*/).map((definition: Definition) => definition/*auto*/.generate(platform/*auto*/)).collect(new Joiner(", ")).orElse("");
		return this/*auto*/.generateWithAfterName(platform/*auto*/, "(" + joinedDefinitions/*auto*/ + ")");
	}
}
