import { ConstructorHeader } from "../../../../magma/app/compile/value/ConstructorHeader";
import { FunctionHeader } from "../../../../magma/app/compile/define/FunctionHeader";
import { Platform } from "../../../../magma/app/io/Platform";
import { Definition } from "../../../../magma/app/compile/define/Definition";
import { List } from "../../../../magma/api/collect/list/List";
import { Joiner } from "../../../../magma/api/collect/Joiner";
export class ConstructorHeader implements FunctionHeader<ConstructorHeader> {
	generateWithAfterName(platform: Platform, afterName: string): string {
		return "constructor " + afterName;
	}
	hasAnnotation(annotation: string): boolean {
		return false;
	}
	removeModifier(modifier: string): ConstructorHeader {
		return this;
	}
	addModifierLast(modifier: string): ConstructorHeader {
		return this;
	}
	generateWithDefinitions0(platform: Platform, definitions: string): string {
		return generateWithAfterName(platform, "(" + definitions + ")");
	}
	generateWithDefinitions(platform: Platform, definitions: List<Definition>): string {
		let joinedDefinitions: string = definitions.query().map((definition: Definition) => definition.generate(platform)).collect(new Joiner(", ")).orElse("");
		return this.generateWithDefinitions0(platform, joinedDefinitions);
	}
}
