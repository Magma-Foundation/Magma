import { ConstructorHeader } from "../../../../magma/app/compile/value/ConstructorHeader";
import { FunctionHeader } from "../../../../magma/app/compile/define/FunctionHeader";
import { Platform } from "../../../../magma/app/io/Platform";
import { Definition } from "../../../../magma/app/compile/define/Definition";
import { List } from "../../../../magma/api/collect/list/List";
import { Joiner } from "../../../../magma/api/collect/Joiner";
export class ConstructorHeader implements FunctionHeader<ConstructorHeader> {
	generateWithAfterName(platform: Platform, afterName: string): string {
		return "constructor " + afterName/*auto*/;
	}
	hasAnnotation(annotation: string): boolean {
		return false/*auto*/;
	}
	removeModifier(modifier: string): ConstructorHeader {
		return this/*auto*/;
	}
	addModifierLast(modifier: string): ConstructorHeader {
		return this/*auto*/;
	}
	generateWithDefinitions0(platform: Platform, definitions: string): string {
		return generateWithAfterName/*auto*/(platform/*auto*/, "(" + definitions/*auto*/ + ")");
	}
	generateWithDefinitions(platform: Platform, definitions: List<Definition>): string {
		let joinedDefinitions: string = definitions/*auto*/.query(/*auto*/).map((definition: Definition) => definition/*auto*/.generate(platform/*auto*/)).collect(new Joiner(", ")).orElse("");
		return this/*auto*/.generateWithDefinitions0(platform/*auto*/, joinedDefinitions/*auto*/);
	}
}
