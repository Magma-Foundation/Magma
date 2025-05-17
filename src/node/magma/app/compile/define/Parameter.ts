import { Definition } from "../../../../magma/app/compile/define/Definition";
import { Option } from "../../../../magma/api/option/Option";
export interface Parameter {
	generate(): string;
	asDefinition(): Option<Definition>;
}
