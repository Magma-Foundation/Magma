import { ConstructorHeader } from "../../../magma/app/compile/ConstructorHeader";
import { MethodHeader } from "../../../magma/app/MethodHeader";
export class ConstructorHeader implements MethodHeader<ConstructorHeader> {
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
