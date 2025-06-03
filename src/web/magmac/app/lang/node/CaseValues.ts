import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Deserializers } from "../../../../magmac/app/lang/Deserializers";
export class CaseValues {
	deserializeOrError(node : Node) : CompileResult<CaseValue> {return Deserializers.orError( "case-value", node, Lists.of( Deserializers.wrap( SingleCaseValue.deserialize), Deserializers.wrap( MultipleCaseValue.deserialize)));;}
}
