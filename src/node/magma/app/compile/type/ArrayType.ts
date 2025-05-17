import { Type } from "../../../../magma/api/Type";
export class ArrayType implements Type {
	child: Type;
	constructor (child: Type) {
		this.child = child;
	}
	generate(): string {
		return this.child.generate() + "[]";
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
