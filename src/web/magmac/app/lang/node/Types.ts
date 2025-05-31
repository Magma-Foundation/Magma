import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { OrRule } from "../../../../magmac/app/compile/rule/OrRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { LazyRule } from "../../../../magmac/app/lang/LazyRule";
import { MutableLazyRule } from "../../../../magmac/app/lang/MutableLazyRule";
import { Deserializers } from "../../../../magmac/app/lang/Deserializers";
export class Types {
	deserialize(node : Node) : CompileResult<JavaType>;
	createTypeRule() : Rule;
}
