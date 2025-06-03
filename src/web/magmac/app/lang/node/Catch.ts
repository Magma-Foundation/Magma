import { Option } from "../../../../magmac/api/Option";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Destructors } from "../../../../magmac/app/lang/Destructors";
import { JavaDefinition } from "../../../../magmac/app/lang/java/JavaDefinition";
export class Catch {
	deserialize(node : Node) : Option<CompileResult<JavaBlockHeader>> {return 0;;}
}
