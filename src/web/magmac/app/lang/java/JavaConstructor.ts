import { Option } from "../../../../magmac/api/Option";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Destructors } from "../../../../magmac/app/lang/Destructors";
export class JavaConstructor {
	deserialize(node : Node) : Option<CompileResult<JavaMethodHeader>> {return Destructors.destructWithType( "constructor", node).map( 0);;}
}
