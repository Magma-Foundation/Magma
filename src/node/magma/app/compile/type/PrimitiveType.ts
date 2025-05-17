import { Type } from "../../../../magma/api/Type";
export class PrimitiveType implements Type {
	static String: PrimitiveType = new PrimitiveType("string");
	static Number: PrimitiveType = new PrimitiveType("number");
	static Var: PrimitiveType = new PrimitiveType("var");
	static Void: PrimitiveType = new PrimitiveType("void");
	static Auto: PrimitiveType = new PrimitiveType("auto");
	static I8: PrimitiveType = new PrimitiveType("I8");
	static I32: PrimitiveType = new PrimitiveType("I32");
	value: string;
	constructor (value: string) {
		this.value = value;
	}
	generate(): string {
		return this.value;
	}
	isFunctional(): boolean {
		return false;
	}
	isVar(): boolean {
		return PrimitiveType.Var === this;
	}
	generateBeforeName(): string {
		return "";
	}
}
