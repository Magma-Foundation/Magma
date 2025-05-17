import { Type } from "../../../../magma/api/Type";
export class ArrayType implements Type {
	child: Type;
	constructor (child: Type) {
		this.child = child;
	}
	generate(): string {
		return this/*auto*/.child.generate(/*auto*/) + "[]";
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
