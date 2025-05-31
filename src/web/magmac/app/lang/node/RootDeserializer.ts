import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Deserializers } from "../../../../magmac/app/lang/Deserializers";
import { Serializable } from "../../../../magmac/app/lang/Serializable";
export class RootDeserializer<T extends Serializable> {
	deserialize(node : Node) : CompileResult<Root<T>>;
}
