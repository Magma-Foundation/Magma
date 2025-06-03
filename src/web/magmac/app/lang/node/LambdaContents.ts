import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Deserializers } from "../../../../magmac/app/lang/Deserializers";
import { JavaRules } from "../../../../magmac/app/lang/JavaRules";
import { JavaLang } from "../../../../magmac/app/lang/java/JavaLang";
export class LambdaContents {
	deserialize(node : Node) : CompileResult<JavaLang.JavaLambdaContent> {return 0.orError( 0, 0, 0.of( 0.wrap( 0.deserializeLambdaValueContent), 0.wrap( 0.JavaLambdaBlockContent.deserialize)));;}
}
