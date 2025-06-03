import { Option } from "../../../../magmac/api/Option";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Destructors } from "../../../../magmac/app/lang/Destructors";
import { JavaLang } from "../../../../magmac/app/lang/java/JavaLang";
export class CaseDefinition {
	deserialize(node : Node) : CompileResult<CaseDefinition> {return 0.destruct( 0).withString( 0).withNodeOptionally( 0, 0.JavaTypes.deserialize).complete( 0);;}
}
