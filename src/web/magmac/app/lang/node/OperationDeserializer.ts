import { Option } from "../../../../magmac/api/Option";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Destructors } from "../../../../magmac/app/lang/Destructors";
import { JavaDeserializers } from "../../../../magmac/app/lang/java/JavaDeserializers";
import { JavaOperation } from "../../../../magmac/app/lang/java/JavaLang/JavaOperation";
export class OperationDeserializer {
	deserialize(node : Node) : Option<CompileResult<JavaOperation>> {return Destructors.destructWithType( this.operator( ).type( ), node).map( 0);;}
}
