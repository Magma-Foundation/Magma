import { Option } from "../../../../magmac/api/Option";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { LocatingRule } from "../../../../magmac/app/compile/rule/LocatingRule";
import { NodeRule } from "../../../../magmac/app/compile/rule/NodeRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { StripRule } from "../../../../magmac/app/compile/rule/StripRule";
import { SuffixRule } from "../../../../magmac/app/compile/rule/SuffixRule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
import { Destructors } from "../../../../magmac/app/lang/Destructors";
import { LazyRule } from "../../../../magmac/app/lang/LazyRule";
export class IndexNode {
	createIndexRule(value : LazyRule) : Rule {;;;;}
	deserialize(value : Node) : Option<CompileResult<IndexNode>> {;;}
	serialize() : Node {;;}
}
