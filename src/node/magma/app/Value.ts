import { Type } from "../../magma/api/Type";
import { CompileState } from "../../magma/app/compile/CompileState";
import { Option } from "../../magma/api/option/Option";
export interface Value extends Argument, Caller  {
	resolve(state: CompileState): Type;
	generateAsEnumValue(structureName: string): Option<string>;
}
