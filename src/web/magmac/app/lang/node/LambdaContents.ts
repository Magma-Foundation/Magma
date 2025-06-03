import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Deserializers } from "../../../../magmac/app/lang/Deserializers";
import { JavaRules } from "../../../../magmac/app/lang/JavaRules";
import { JavaLang } from "../../../../magmac/app/lang/java/JavaLang";
export class LambdaContents {
	public static deserialize( node : Node) : CompileResult<JavaLang.JavaLambdaContent> {return Deserializers.orError( "lambda-content", node, Lists.of( Deserializers.wrap( JavaRules.deserializeLambdaValueContent), Deserializers.wrap( JavaLang.JavaLambdaBlockContent.deserialize)));;}
}
