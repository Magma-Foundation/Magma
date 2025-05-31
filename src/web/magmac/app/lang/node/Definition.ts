import { Option } from "../../../../magmac/api/Option";
import { List } from "../../../../magmac/api/collect/list/List";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { InitialDestructor } from "../../../../magmac/app/compile/node/InitialDestructor";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Deserializers } from "../../../../magmac/app/lang/Deserializers";
import { Serializable } from "../../../../magmac/app/lang/Serializable";
export class Definition<T> {
	deserialize0(deserialize : InitialDestructor) : CompileResult<Definition<JavaType>>;
	deserialize(node : Node) : CompileResult<Definition<JavaType>>;
	deserializeWithType(node : Node) : Option<CompileResult<Definition<JavaType>>>;
	withType(newType : T) : Definition<T>;
	withName(name : String) : Definition<T>;
}
