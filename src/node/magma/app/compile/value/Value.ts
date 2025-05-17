import { Option } from "../../../../magma/api/option/Option";
import { Platform } from "../../../../magma/app/io/Platform";
import { Type } from "../../../../magma/api/Type";
export interface Value extends Argument, Caller  {
	generateAsEnumValue(structureName: string, platform: Platform): Option<string>;
	type(): Type;
}
