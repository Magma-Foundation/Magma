import { Option } from "../../../../magmac/api/Option";
import { List } from "../../../../magmac/api/collect/list/List";
import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { LocatingRule } from "../../../../magmac/app/compile/rule/LocatingRule";
import { NodeListRule } from "../../../../magmac/app/compile/rule/NodeListRule";
import { NodeRule } from "../../../../magmac/app/compile/rule/NodeRule";
import { OrRule } from "../../../../magmac/app/compile/rule/OrRule";
import { PrefixRule } from "../../../../magmac/app/compile/rule/PrefixRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { StringRule } from "../../../../magmac/app/compile/rule/StringRule";
import { StripRule } from "../../../../magmac/app/compile/rule/StripRule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
import { Destructors } from "../../../../magmac/app/lang/Destructors";
import { TypescriptLang } from "../../../../magmac/app/lang/web/TypescriptLang";
import { CaseDefinition } from "../../../../magmac/app/lang/node/CaseDefinition";
import { CaseValue } from "../../../../magmac/app/lang/node/CaseValue";
import { CaseValues } from "../../../../magmac/app/lang/node/CaseValues";
import { MultipleCaseValue } from "../../../../magmac/app/lang/node/MultipleCaseValue";
import { SingleCaseValue } from "../../../../magmac/app/lang/node/SingleCaseValue";
export class JavaCaseNode {
	deserialize(node : Node) : Option<CompileResult<JavaCaseNode>> {return 0;;}
	createCaseRule(value : Rule, segment : Rule) : Rule {break;break;break;break;break;break;break;return 0;;}
	serialize() : Node {return 0;;}
}
