import { Type } from "../../magma/api/Type";
import { Platform } from "../../magma/app/Platform";
class BooleanType implements Type {
	platform: Platform;
	constructor (platform: Platform) {
		this.platform = platform;
	}
	generate(): string {
		if (Platform.TypeScript === this.platform) {
			return "boolean";
		}
		return "Bool";
	}
	isFunctional(): boolean {
		return false;
	}
	isVar(): boolean {
		return false;
	}
	generateBeforeName(): string {
		return "";
	}
}
