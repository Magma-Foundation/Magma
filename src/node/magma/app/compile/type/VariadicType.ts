import { Type } from "../../../../magma/api/Type";
export class VariadicType implements Type {
	type: Type;
	constructor (type: Type) {
		this.type = type;
	}
	generate(): string {
		return this.type.generate() + "[]";
	}
	isFunctional(): boolean {
		return false;
	}
	isVar(): boolean {
		return false;
	}
	generateBeforeName(): string {
		return "...";
	}
}
