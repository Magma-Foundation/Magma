import { Option } from "../../../../magmac/api/Option";
import { List } from "../../../../magmac/api/collect/list/List";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { InitialDestructor } from "../../../../magmac/app/compile/node/InitialDestructor";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Destructors } from "../../../../magmac/app/lang/Destructors";
import { Serializable } from "../../../../magmac/app/lang/Serializable";
import { Annotation } from "../../../../magmac/app/lang/common/Annotation";
export class Definition<T> {
	deserialize0(deserialize : InitialDestructor) : CompileResult<Definition<JavaType>> {break;;}
	deserialize(node : Node) : CompileResult<Definition<JavaType>> {break;;}
	deserializeWithType(node : Node) : Option<CompileResult<Definition<JavaType>>> {break;;}
	withType(newType : T) : Definition<T> {break;;}
	withName(name : String) : Definition<T> {break;;}
	serialize() : Node {break;;}
}
