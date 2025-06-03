import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Serializable } from "../../../../magmac/app/lang/Serializable";
export interface Deserializer<T> {
	 deserialize( node : Node) : CompileResult<T>;
}
