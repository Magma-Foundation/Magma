import { Option } from "../../../magmac/api/Option";
import { CompileResult } from "../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../magmac/app/compile/node/Node";
export interface Deserializer<T> {
	 deserialize( node : Node) : Option<CompileResult<T>>;
}
