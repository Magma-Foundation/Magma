import { Platform } from "../../../../magma/app/io/Platform";
import { Definition } from "../../../../magma/app/compile/define/Definition";
import { Option } from "../../../../magma/api/option/Option";
export interface Parameter {generate(platform: Platform): string;asDefinition(): Option<Definition>;
}
