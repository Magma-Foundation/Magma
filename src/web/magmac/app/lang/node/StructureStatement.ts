import { Option } from "../../../../magmac/api/Option";
import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { NodeRule } from "../../../../magmac/app/compile/rule/NodeRule";
import { OrRule } from "../../../../magmac/app/compile/rule/OrRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { StripRule } from "../../../../magmac/app/compile/rule/StripRule";
import { SuffixRule } from "../../../../magmac/app/compile/rule/SuffixRule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
import { Deserializers } from "../../../../magmac/app/lang/Deserializers";
import { LazyRule } from "../../../../magmac/app/lang/LazyRule";
export class StructureStatement {
	deserialize(node : Node) : Option<CompileResult<JavaStructureMember>>;
	createStructureStatementRule(definitionRule : Rule, valueRule : LazyRule) : Rule;
}
