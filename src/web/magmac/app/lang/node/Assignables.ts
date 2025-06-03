import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Deserializers } from "../../../../magmac/app/lang/Deserializers";
import { JavaDeserializers } from "../../../../magmac/app/lang/java/JavaDeserializers";
import { JavaLang } from "../../../../magmac/app/lang/java/JavaLang";
export class Assignables {
	deserializeError(node : Node) : CompileResult<JavaLang.JavaAssignable> {return 0.orError( 0, 0, 0.of( 0.wrap( 0.deserializeTypedDefinition), 0.wrap( 0.deserializeValue)));;}
}
