import { Caller } from "../../../../magma/app/compile/value/Caller";
import { Platform } from "../../../../magma/app/io/Platform";
import { Value } from "../../../../magma/app/compile/value/Value";
import { Option } from "../../../../magma/api/option/Option";
import { None } from "../../../../magma/api/option/None";
export class ConstructionCaller implements Caller {
	right: string;
	platform: Platform;
	constructor (right: string, platform: Platform) {
		this.right = right;
		this.platform = platform;
	}
	generate(platform: Platform): string {
		if (Platform.Magma === this.platform) {
			return this.right;
		}
		return "new " + this.right;
	}
	findChild(): Option<Value> {
		return new None<Value>();
	}
}
