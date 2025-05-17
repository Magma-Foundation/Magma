import { Type } from "../../../../magma/api/Type";
import { Platform } from "../../../../magma/app/io/Platform";
export class BooleanType implements Type {
	platform: Platform;
	constructor (platform: Platform) {
		this.platform = platform;
	}
	generate(): string {
		if (Platform/*auto*/.TypeScript === this/*auto*/.platform) {
			return "boolean";
		}
		return "Bool";
	}
	isFunctional(): boolean {
		return false/*auto*/;
	}
	isVar(): boolean {
		return false/*auto*/;
	}
	generateBeforeName(): string {
		return "";
	}
}
