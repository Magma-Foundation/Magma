import { Main } from "../../../../magma/app/Main";
import { Platform } from "../../../../magma/app/io/Platform";
import { Value } from "../../../../magma/app/compile/value/Value";
import { Option } from "../../../../magma/api/option/Option";
import { None } from "../../../../magma/api/option/None";
import { Definition } from "../../../../magma/app/compile/define/Definition";
import { Type } from "../../../../magma/api/Type";
import { CompileState } from "../../../../magma/app/compile/CompileState";
import { PrimitiveType } from "../../../../magma/app/compile/type/PrimitiveType";
export class Placeholder {
	input: string;
	constructor (input: string) {
		this.input = input;
	}
	generate(): string {
		return Main/*auto*/.generatePlaceholder(this/*auto*/.input);
	}
	isFunctional(): boolean {
		return false/*auto*/;
	}
	generate(platform: Platform): string {
		return this/*auto*/.generate(/*auto*/);
	}
	findChild(): Option<Value> {
		return new None<Value>(/*auto*/);
	}
	asDefinition(): Option<Definition> {
		return new None<Definition>(/*auto*/);
	}
	toValue(): Option<Value> {
		return new None<Value>(/*auto*/);
	}
	resolve(state: CompileState): Type {
		return PrimitiveType/*auto*/.Auto;
	}
	isVar(): boolean {
		return false/*auto*/;
	}
	generateBeforeName(): string {
		return "";
	}
	generateAsEnumValue(structureName: string, platform: Platform): Option<string> {
		return new None<string>(/*auto*/);
	}
}
