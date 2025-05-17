// []
import { Type } from "../../../../magma/api/Type";
export class SliceType implements Type {
	type: Type;
	constructor (type: Type) {
		this.type = type;
	}
	generate(): string {
		return "&[" + this/*auto*/.type.generate(/*auto*/) + "]";
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
