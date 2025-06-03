import { Option } from "../../../../magmac/api/Option";
import { List } from "../../../../magmac/api/collect/list/List";
import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { FilterRule } from "../../../../magmac/app/compile/rule/FilterRule";
import { LocatingRule } from "../../../../magmac/app/compile/rule/LocatingRule";
import { OrRule } from "../../../../magmac/app/compile/rule/OrRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { StringRule } from "../../../../magmac/app/compile/rule/StringRule";
import { StripRule } from "../../../../magmac/app/compile/rule/StripRule";
import { SuffixRule } from "../../../../magmac/app/compile/rule/SuffixRule";
import { CommonRules } from "../../../../magmac/app/lang/CommonRules";
import { Destructors } from "../../../../magmac/app/lang/Destructors";
import { Arguments } from "../../../../magmac/app/lang/node/Arguments";
export class JavaEnumValue {
	deserialize(node : Node) : CompileResult<JavaEnumValue> {return 0;;}
	createEnumValueRule(value : Rule) : Rule {break;break;return 0;;}
}
