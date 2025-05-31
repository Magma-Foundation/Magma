import { Option } from "../../../../magmac/api/Option";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { NodeRule } from "../../../../magmac/app/compile/rule/NodeRule";
import { PrefixRule } from "../../../../magmac/app/compile/rule/PrefixRule";
import { StripRule } from "../../../../magmac/app/compile/rule/StripRule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
import { LazyRule } from "../../../../magmac/app/lang/LazyRule";
import { Deserializers } from "../../../../magmac/app/lang/Deserializers";
export class Not {
	createNotRule(value : LazyRule) : TypeRule;
	deserialize(node : Node) : Option<CompileResult<Value>>;
}
