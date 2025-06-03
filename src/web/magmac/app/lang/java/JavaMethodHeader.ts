import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Deserializers } from "../../../../magmac/app/lang/Deserializers";
export interface JavaMethodHeader {
	static deserializeError( node : Node) : CompileResult<JavaMethodHeader> {return Deserializers.orError( "header", node, Lists.of( Deserializers.wrap( JavaDeserializers.deserializeTypedDefinition), Deserializers.wrap( JavaConstructor.deserialize)));;}
}
