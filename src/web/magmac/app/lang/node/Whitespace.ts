import { Option } from "../../../../magmac/api/Option";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { ExactRule } from "../../../../magmac/app/compile/rule/ExactRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { StripRule } from "../../../../magmac/app/compile/rule/StripRule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
import { Destructors } from "../../../../magmac/app/lang/Destructors";
import { Lang } from "../../../../magmac/app/lang/java/Lang";
export class Whitespace {
	deserialize(node : Node) : Option<CompileResult<Whitespace>> {;;}
	createWhitespaceRule() : Rule {;;}
	serialize() : Node {;;}
}
