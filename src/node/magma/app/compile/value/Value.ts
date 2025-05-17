import { Type } from "../../../../magma/api/Type";
import { CompileState } from "../../../../magma/app/compile/CompileState";
import { Option } from "../../../../magma/api/option/Option";
import { Platform } from "../../../../magma/app/io/Platform";
export interface Value extends Argument, Caller  {resolve(state: CompileState): Type;generateAsEnumValue(structureName: string, platform: Platform): Option<string>;
}
