import { Definition } from "magma/app/Definition";
import { Option } from "magma/api/option/Option";
interface Parameter {
	mut generate(): &[I8];
	mut asDefinition(): Option<Definition>;
}
