import { Platform } from "../../../../magma/app/io/Platform";
import { Value } from "../../../../magma/app/compile/value/Value";
import { Option } from "../../../../magma/api/option/Option";
export interface Caller {generate(platform: Platform): string;findChild(): Option<Value>;
}
