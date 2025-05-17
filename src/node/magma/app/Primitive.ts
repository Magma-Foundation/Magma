import { Type } from "../../magma/api/Type";
export class Primitive implements Type {
	static String: Primitive = new Primitive("string");
	static Number: Primitive = new Primitive("number");
	static Var: Primitive = new Primitive("var");
	static Void: Primitive = new Primitive("void");
	static Unknown: Primitive = new Primitive("unknown");
	static I8: Primitive = new Primitive("I8");
	static I32: Primitive = new Primitive("I32");
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
		return Primitive.Var === this;
	}
	generateBeforeName(): string {
		return "";
	}
}
