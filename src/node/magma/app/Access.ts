import { Value } from "../../magma/app/Value";
import { Option } from "../../magma/api/option/Option";
import { Some } from "../../magma/api/option/Some";
import { Type } from "../../magma/api/Type";
import { CompileState } from "../../magma/app/compile/CompileState";
import { Primitive } from "../../magma/app/Primitive";
import { None } from "../../magma/api/option/None";
class Access implements Value {
	child: Value;
	property: string;
	constructor (child: Value, property: string) {
		this.child = child;
		this.property = property;
	}
	generate(): string {
		return this.child.generate() + "." + this.property;
	}
	toValue(): Option<Value> {
		return new Some<Value>(this);
	}
	findChild(): Option<Value> {
		return new Some<Value>(this.child);
	}
	resolve(state: CompileState): Type {
		return Primitive.Unknown;
	}
	generateAsEnumValue(structureName: string): Option<string> {
		return new None<string>();
	}
}
