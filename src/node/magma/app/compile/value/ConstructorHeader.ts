import { ConstructorHeader } from "../../../../magma/app/compile/value/ConstructorHeader";
import { FunctionHeader } from "../../../../magma/app/compile/define/FunctionHeader";
export class ConstructorHeader implements FunctionHeader<ConstructorHeader> {
	generateWithAfterName(afterName: string): string {
		return "constructor " + afterName;
	}
	hasAnnotation(annotation: string): boolean {
		return false;
	}
	removeModifier(modifier: string): ConstructorHeader {
		return this;
	}
	addModifier(modifier: string): ConstructorHeader {
		return this;
	}
}
